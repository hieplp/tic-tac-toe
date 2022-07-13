package com.hieplp.tictactoe.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.hieplp.tictactoe.interceptor.TokenInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 23/06/2022
 * Time: 11:17
 */
public class InterceptorModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Binding interceptors");
        bind(TokenInterceptor.class).in(Scopes.SINGLETON);
    }
}
