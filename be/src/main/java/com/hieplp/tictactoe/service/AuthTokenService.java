package com.hieplp.tictactoe.service;

import com.google.inject.Inject;
import com.hieplp.tictactoe.common.model.UserModel;
import com.hieplp.tictactoe.common.util.JsonConverter;
import com.hieplp.tictactoe.interceptor.TokenInterceptor;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tictactoe.AuthTokenServiceGrpc;
import tictactoe.LogoutRequest;
import tictactoe.LogoutResponse;

import java.util.Map;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 23/06/2022
 * Time: 10:49
 */
public class AuthTokenService extends AuthTokenServiceGrpc.AuthTokenServiceImplBase {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final Map<String, UserModel> userMap;

    @Inject
    public AuthTokenService(Map<String, UserModel> userMap) {
        this.userMap = userMap;
    }

    @Override
    public void logout(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
        LOGGER.info("Logout with request {}", JsonConverter.toJson(request));
        try {
            UserModel userModel = TokenInterceptor.USER_MODEL_KEY.get();
            userMap.remove(userModel.getUserId());

            LogoutResponse response = LogoutResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
