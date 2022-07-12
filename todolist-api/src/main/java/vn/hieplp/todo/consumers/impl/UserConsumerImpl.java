package vn.hieplp.todo.consumers.impl;

import com.google.inject.Inject;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.app.App;
import vn.hieplp.todo.common.constant.Constants;
import vn.hieplp.todo.common.handler.IValidationHandler;
import vn.hieplp.todo.common.request.auth.*;
import vn.hieplp.todo.config.modules.pattern.IExecutor;
import vn.hieplp.todo.consumers.IUserConsumer;
import vn.hieplp.todo.services.IUserService;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 11:18
 */
public class UserConsumerImpl implements IUserConsumer {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final IValidationHandler validationHandler;
    private final IUserService userService;

    @Inject
    public UserConsumerImpl(IValidationHandler validationHandler,
                            IUserService userService) {
        this.validationHandler = validationHandler;
        this.userService = userService;
    }

    @Override
    public void handleRegister(RoutingContext context) {
        LOGGER.info("Handle register");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .body()
                .setRequestClass(RegisterRequest.class)
                .execute(context, commonRequest -> {
                    validationHandler
                            .checkCanNullWithAnnotation(commonRequest.getRequest())
                            .checkLength(commonRequest.getRequest())
                            .checkValidEmail(commonRequest.getRequest());
                    return userService.register((RegisterRequest) commonRequest.getRequest());
                }, executor::created);
    }

    @Override
    public void handleLogin(RoutingContext context) {
        LOGGER.info("Handle login");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .body()
                .setRequestClass(LoginRequest.class)
                .execute(context, commonRequest -> {
                    validationHandler.checkNullAll(commonRequest.getRequest());
                    return userService.login((LoginRequest) commonRequest.getRequest());
                }, executor::ok);
    }

    @Override
    public void handleForgotPassword(RoutingContext context) {
        LOGGER.info("Handle send forgot password email");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .body()
                .setRequestClass(ForgotPasswordRequest.class)
                .execute(context, commonRequest -> {
                    validationHandler.checkNullAll(commonRequest.getRequest());
                    userService.forgotPassword((ForgotPasswordRequest) commonRequest.getRequest());
                    return new Object();
                }, executor::ok);
    }

    @Override
    public void handleResetPassword(RoutingContext context) {
        LOGGER.info("Handle reset password");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .body()
                .setRequestClass(ResetPasswordRequest.class)
                .execute(context, commonRequest -> {
                    validationHandler.checkNullAll(commonRequest.getRequest());
                    userService.resetPassword((ResetPasswordRequest) commonRequest.getRequest());
                    return new Object();
                }, executor::ok);
    }

    @Override
    public void handleChangePassword(RoutingContext context) {
        LOGGER.info("Handle change password");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .body()
                .setRequestClass(ChangePasswordRequest.class)
                .execute(context, commonRequest -> {
                    validationHandler.checkNotNullWithAnnotation(commonRequest.getRequest());
                    ChangePasswordRequest request = (ChangePasswordRequest) commonRequest.getRequest();
                    request.setUserId(commonRequest.getHeaders().getUserId());
                    userService.changePassword(request);
                    return new Object();
                }, executor::ok);

    }

    @Override
    public void handleCheckEmailExists(RoutingContext context) {
        LOGGER.info("Handle check email exists");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .execute(context, commonRequest -> {
                    String email = context.request().getParam(Constants.Path.User.EMAIL);
                    return userService.doesEmailExist(email);
                }, executor::ok);
    }

    @Override
    public void handleCheckRequestValid(RoutingContext context) {
        LOGGER.info("Handle check request is valid");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .execute(context, commonRequest -> {
                    String requestId = context.request().getParam(Constants.Path.User.REQUEST_ID);
                    return userService.isRequestValid(requestId);
                }, executor::ok);

    }
}
