package com.hieplp.tictactoe.common.grpc;

import io.grpc.BindableService;
import io.grpc.ServerInterceptor;

import java.io.IOException;
import java.util.List;

public interface IGRPCServer {
    IGRPCServer addServices(List<BindableService> services);

    IGRPCServer addService(BindableService bindableService, ServerInterceptor interceptor);

    IGRPCServer start() throws IOException;

    IGRPCServer intercept(ServerInterceptor interceptor);


    void blockUntilShutdown() throws InterruptedException;
}
