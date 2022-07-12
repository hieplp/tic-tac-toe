package vn.hieplp.todo.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.handler.IAuthHandler;
import vn.hieplp.todo.common.handler.IRedisHandler;
import vn.hieplp.todo.common.handler.IValidationHandler;
import vn.hieplp.todo.common.handler.impl.AuthHandlerImpl;
import vn.hieplp.todo.common.handler.impl.RedisHandlerImpl;
import vn.hieplp.todo.common.handler.impl.ValidationHandlerImpl;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 15:43
 */
public class HandlerModule extends AbstractModule {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Start loading handlers");
        bind(IAuthHandler.class).to(AuthHandlerImpl.class).in(Scopes.SINGLETON);
        bind(IValidationHandler.class).to(ValidationHandlerImpl.class).in(Scopes.SINGLETON);
        bind(IRedisHandler.class).to(RedisHandlerImpl.class).in(Scopes.SINGLETON);
    }
}
