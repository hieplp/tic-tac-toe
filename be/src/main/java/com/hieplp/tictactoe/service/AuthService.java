package com.hieplp.tictactoe.service;

import com.google.inject.Inject;
import com.hieplp.tictactoe.common.enums.StaticEnums;
import com.hieplp.tictactoe.common.handler.IAuthHandler;
import com.hieplp.tictactoe.common.model.UserModel;
import com.hieplp.tictactoe.common.util.JsonConverter;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tictactoe.AuthServiceGrpc;
import tictactoe.CreateTempUserRequest;
import tictactoe.CreateTempUserResponse;
import tictactoe.TokenModel;

import java.util.Map;
import java.util.UUID;


/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 14/06/2022
 * Time: 14:56
 */
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final Map<String, UserModel> userMap;
    private final IAuthHandler authHandler;

    @Inject
    public AuthService(Map<String, UserModel> userMap,
                       IAuthHandler authHandler) {
        this.userMap = userMap;
        this.authHandler = authHandler;
    }

    @Override
    public void createTempUser(CreateTempUserRequest request, StreamObserver<CreateTempUserResponse> responseObserver) {
        LOGGER.info("Create temp user with request: {}", JsonConverter.toJson(request));
        try {
            // Init user model then add to user map
            UserModel userModel = UserModel.builder()
                    .userId(UUID.randomUUID().toString())
                    .userName(request.getUserName())
                    .userStatus(StaticEnums.UserStatus.ACTIVE.getStatus())
                    .build();
            userMap.put(userModel.getUserId(), userModel);

            // Token
            TokenModel tokenModel = authHandler.generateToken(userModel);
            // Init response
            CreateTempUserResponse response = CreateTempUserResponse.newBuilder()
                    .setUserId(userModel.getUserId())
                    .setUserName(userModel.getUserName())
                    .setUserStatus(userModel.getUserStatus())
                    .setToken(tokenModel)
                    .build();

            LOGGER.info("{}", response);

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            LOGGER.error("Unknown error when create temporary user: {}", e.getMessage());
            responseObserver.onError(e);
        }
    }
}
