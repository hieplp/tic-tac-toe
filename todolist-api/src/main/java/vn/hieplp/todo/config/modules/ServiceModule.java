package vn.hieplp.todo.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.services.*;
import vn.hieplp.todo.services.impl.*;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:51
 */
public class ServiceModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Start loading services");
        bind(IUserService.class).to(UserServiceImpl.class).in(Scopes.SINGLETON);
        bind(ITaskService.class).to(TaskServiceImpl.class).in(Scopes.SINGLETON);
        bind(INoteService.class).to(NoteServiceImpl.class).in(Scopes.SINGLETON);
        bind(ITemplateService.class).to(TemplateServiceImpl.class).in(Scopes.SINGLETON);
        bind(IFileService.class).to(FileServiceImpl.class).in(Scopes.SINGLETON);
    }
}
