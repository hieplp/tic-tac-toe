package com.hieplp.tictactoe.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.hieplp.tictactoe.service.AuthService;
import com.hieplp.tictactoe.service.AuthTokenService;
import com.hieplp.tictactoe.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 20/06/2022
 * Time: 09:58
 */
public class ServiceModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Binding services");
        bind(AuthService.class).in(Scopes.SINGLETON);
        bind(AuthTokenService.class).in(Scopes.SINGLETON);
        bind(GameService.class).in(Scopes.SINGLETON);
    }
}
