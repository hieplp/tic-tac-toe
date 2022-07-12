package vn.hieplp.todo.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.repositories.sources.INoteRepository;
import vn.hieplp.todo.repositories.sources.ITaskRepository;
import vn.hieplp.todo.repositories.sources.ITemplateRepository;
import vn.hieplp.todo.repositories.sources.IUserRepository;
import vn.hieplp.todo.repositories.sources.impl.NoteRepositoryImpl;
import vn.hieplp.todo.repositories.sources.impl.TaskRepositoryImpl;
import vn.hieplp.todo.repositories.sources.impl.TemplateRepositoryIml;
import vn.hieplp.todo.repositories.sources.impl.UserRepositoryImpl;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:51
 */
public class RepositoryModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Start loading repositories");
        bind(IUserRepository.class).to(UserRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(ITaskRepository.class).to(TaskRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(INoteRepository.class).to(NoteRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(ITemplateRepository.class).to(TemplateRepositoryIml.class).in(Scopes.SINGLETON);
    }
}
