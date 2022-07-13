package com.hieplp.tictactoe.interceptor;

import com.google.inject.Inject;
import com.hieplp.tictactoe.common.handler.IAuthHandler;
import com.hieplp.tictactoe.common.model.UserModel;
import io.grpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 20/06/2022
 * Time: 14:47
 */
public class TokenInterceptor implements ServerInterceptor {

    public static final Context.Key<UserModel> USER_MODEL_KEY = Context.key("userModel");
    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final IAuthHandler authHandler;
    private final Map<String, UserModel> userMap;

    @Inject
    public TokenInterceptor(IAuthHandler authHandler, Map<String, UserModel> userMap) {
        this.authHandler = authHandler;
        this.userMap = userMap;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        LOGGER.info("Auth interceptor call");
        try {
            // Validate token
            Metadata.Key<String> tokenKey = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
            String token = headers.get(tokenKey);
            // Check if user exists
            UserModel userModel = authHandler.validateToken(token);
            if (!userMap.containsKey(userModel.getUserId())) {
                LOGGER.error("User not found");
                call.close(Status.UNAUTHENTICATED.withDescription("User not found"), new Metadata());
                return new ServerCall.Listener<>() {
                };
            }

            Context context = Context.current().withValue(USER_MODEL_KEY, userModel);
            return Contexts.interceptCall(context, call, headers, next);
        } catch (Exception e) {
            LOGGER.error("Auth error: {}", e.getMessage());
            call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()), new Metadata());
            return new ServerCall.Listener<>() {
            };
        }
    }
}
