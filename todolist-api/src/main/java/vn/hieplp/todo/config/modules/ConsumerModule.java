package vn.hieplp.todo.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.consumers.IFileConsumer;
import vn.hieplp.todo.consumers.INoteConsumer;
import vn.hieplp.todo.consumers.ITaskConsumer;
import vn.hieplp.todo.consumers.IUserConsumer;
import vn.hieplp.todo.consumers.impl.FileConsumerImpl;
import vn.hieplp.todo.consumers.impl.NoteConsumerImpl;
import vn.hieplp.todo.consumers.impl.TaskConsumerImpl;
import vn.hieplp.todo.consumers.impl.UserConsumerImpl;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:51
 */
public class ConsumerModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Start loading consumers");
        bind(IUserConsumer.class).to(UserConsumerImpl.class).in(Scopes.SINGLETON);
        bind(ITaskConsumer.class).to(TaskConsumerImpl.class).in(Scopes.SINGLETON);
        bind(INoteConsumer.class).to(NoteConsumerImpl.class).in(Scopes.SINGLETON);
        bind(IFileConsumer.class).to(FileConsumerImpl.class).in(Scopes.SINGLETON);
    }
}
