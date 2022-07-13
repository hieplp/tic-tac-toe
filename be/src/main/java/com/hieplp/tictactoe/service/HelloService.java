package com.hieplp.tictactoe.service;

import com.hieplp.tictactoe.HelloRequest;
import com.hieplp.tictactoe.HelloResponse;
import com.hieplp.tictactoe.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 14/06/2022
 * Time: 13:42
 */
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        System.out.println(request.getFirstName() + "--" + request.getLastName());

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting("HELL")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
