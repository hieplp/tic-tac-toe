package com.hieplp.tictactoe.common.grpc;

import com.hieplp.tictactoe.common.config.GRPCConfig;
import com.hieplp.tictactoe.common.util.States;
import io.grpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 14/06/2022
 * Time: 14:34
 */
public class GRPCServer implements IGRPCServer {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final ServerBuilder<?> serverBuilder;
    private Server server;

    public GRPCServer(GRPCConfig config) {
        serverBuilder = ServerBuilder.forPort(config.getPort());
    }

    @Override
    public IGRPCServer addServices(List<BindableService> services) {
        services.forEach(serverBuilder::addService);
        return this;
    }

    @Override
    public IGRPCServer addService(BindableService bindableService, ServerInterceptor interceptor) {
        serverBuilder.addService(ServerInterceptors.intercept(bindableService, interceptor));
        return this;
    }

    @Override
    public IGRPCServer start() throws IOException {
        server = serverBuilder
                .build()
                .start();
        return this;
    }

    @Override
    public IGRPCServer intercept(ServerInterceptor interceptor) {
        serverBuilder.intercept(interceptor);
        return this;
    }


    @Override
    public void blockUntilShutdown() throws InterruptedException {
        if (States.isNotNull(server)) {
            server.awaitTermination();
        }
    }
}
