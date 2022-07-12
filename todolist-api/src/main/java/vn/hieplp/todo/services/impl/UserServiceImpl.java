package vn.hieplp.todo.services.impl;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.config.ConfigInfo;
import vn.hieplp.todo.common.constant.TemplateConstant;
import vn.hieplp.todo.common.exceptions.AuthException;
import vn.hieplp.todo.common.exceptions.DatabaseException;
import vn.hieplp.todo.common.handler.IAuthHandler;
import vn.hieplp.todo.common.handler.IRedisHandler;
import vn.hieplp.todo.common.models.TokenModel;
import vn.hieplp.todo.common.models.UserModel;
import vn.hieplp.todo.common.request.auth.*;
import vn.hieplp.todo.common.response.auth.LoginResponse;
import vn.hieplp.todo.common.response.auth.RegisterResponse;
import vn.hieplp.todo.common.utils.Generator;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.common.utils.States;
import vn.hieplp.todo.repositories.generates.tables.records.PasswordRecord;
import vn.hieplp.todo.repositories.generates.tables.records.UserRecord;
import vn.hieplp.todo.repositories.sources.IUserRepository;
import vn.hieplp.todo.services.ITemplateService;
import vn.hieplp.todo.services.IUserService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:57
 */
public class UserServiceImpl implements IUserService {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final IUserRepository userRepository;
    private final IAuthHandler authHandler;
    private final ITemplateService templateService;
    private final IRedisHandler redisHandler;
    private final ConfigInfo configInfo;

    @Inject
    public UserServiceImpl(IUserRepository userRepository, IAuthHandler authHandler, ITemplateService templateService, IRedisHandler redisHandler, ConfigInfo configInfo) {
        this.userRepository = userRepository;
        this.authHandler = authHandler;
        this.templateService = templateService;
        this.redisHandler = redisHandler;
        this.configInfo = configInfo;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        LOGGER.info("Login with request {}", JsonConverter.toJson(request));
        // Check user with email exists
        UserModel user = userRepository.getUserModelByEmail(request.getEmail());
        // Validate password
        isPasswordCorrect(user.getUserId(), request.getPassword());
        // Generate token
        TokenModel token = authHandler.generateToken(user);

        return LoginResponse.builder().user(user).token(token).build();
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        LOGGER.info("Register with request {}", JsonConverter.toJson(request));
        // Save user
        String userId = Generator.generateRandomString(8);
        request.setUserId(userId);
        userRepository.register(request);
        UserModel user = this.getUserModel(userId);
        // Generate token
        TokenModel token = authHandler.generateToken(user);

        return RegisterResponse.builder().user(user).token(token).build();
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        LOGGER.info("Reset password with request {}", JsonConverter.toJson(request));
        String userId = redisHandler.getValueNotNull(request.getRequestId());
        userRepository.updatePassword(userId, request.getPassword());
        // Delete key in redis
        redisHandler.delete(request.getRequestId());
        redisHandler.delete(userId);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        LOGGER.info("Send forgot password email with request {}", JsonConverter.toJson(request));
        // Check if email exists in database
        UserRecord userRecord = userRepository.getUserRecordByEmail(request.getEmail());
        // Check if forgot password request exists in redis
        if (States.isNotNull(redisHandler.getValueNull(userRecord.getUserid()))) {
            throw new DatabaseException.DuplicateException("Forgot password request sent");
        }
        // Save to redis
        String requestId = Generator.generateRandomString(40).toLowerCase(Locale.ROOT);
        redisHandler.save(userRecord.getUserid(), requestId, configInfo.getRedisExpireTime().getForgotPassword());
        redisHandler.save(requestId, userRecord.getUserid(), configInfo.getRedisExpireTime().getForgotPassword());
        // Send email
        Map<String, String> params = new HashMap<>();
        String resetPasswordLink = configInfo.getFeServer().getHost() + configInfo.getFeServer().getResetPasswordUrl() + "/" + requestId;
        params.put(TemplateConstant.Params.ForgotPassword.LINK, resetPasswordLink);
        params.put(TemplateConstant.Params.ForgotPassword.FULL_NAME, userRecord.getFullname());
        templateService.sendEmail(userRecord.getEmail(), TemplateConstant.Template.FORGOT_PASSWORD, params);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        LOGGER.info("Change password with request {}", JsonConverter.toJson(request));
        // Check if old password is correct
        this.isPasswordCorrect(request.getUserId(), request.getOldPassword());
        // Update password with new password;
        userRepository.updatePassword(request.getUserId(), request.getNewPassword());
    }

    @Override
    public UserModel getUserModel(String userId) {
        LOGGER.info("Get user model by userId {}", userId);
        return userRepository.getUserModel(userId);
    }

    @Override
    public boolean doesEmailExist(String email) {
        LOGGER.info("Check if email {} exists", email);
        return userRepository.doesEmailExist(email);
    }

    @Override
    public boolean isRequestValid(String requestId) {
        LOGGER.info("Check if request is timeout with requestId {}", requestId);
        return States.isNotNull(redisHandler.getValueNull(requestId));
    }

    private void isPasswordCorrect(String userId, String password) {
        LOGGER.info("Check if password is correct with userId {}", userId);
        PasswordRecord passwordRecord = userRepository.getPasswordRecord(userId);
        if (!authHandler.validatePassword(password, passwordRecord.getPassword(), passwordRecord.getSalt())) {
            throw new AuthException.InvalidPasswordException("Invalid password");
        }
    }
}
