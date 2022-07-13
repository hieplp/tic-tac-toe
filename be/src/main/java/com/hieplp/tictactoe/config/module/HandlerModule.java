package com.hieplp.tictactoe.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.hieplp.tictactoe.common.handler.IAuthHandler;
import com.hieplp.tictactoe.common.handler.impl.AuthHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 20/06/2022
 * Time: 10:00
 */
public class HandlerModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Binding handlers");
        bind(IAuthHandler.class).to(AuthHandler.class).in(Scopes.SINGLETON);
    }
}
