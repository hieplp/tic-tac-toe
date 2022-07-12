package vn.hieplp.todo.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.exceptions.CommonException;
import vn.hieplp.todo.config.ModuleConfig;
import vn.hieplp.todo.consumers.IConsumer;
import vn.hieplp.todo.consumers.impl.ConsumerIml;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 15:41
 */
public class App extends AbstractVerticle {

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public static Injector injector;

    @Override
    public void start() throws Exception {
        LOGGER.info("Starting todolist service");

        if (context.config().isEmpty()) {
            LOGGER.error("Not found config file");
            throw new CommonException.InvalidConfig("Not found config file");
        }

        injector = Guice.createInjector(new ModuleConfig(context));
        IConsumer consumer = injector.getInstance(ConsumerIml.class);
        consumer
                .enableCorsSupport()
                .usingRestful()
                .start();
    }
}
