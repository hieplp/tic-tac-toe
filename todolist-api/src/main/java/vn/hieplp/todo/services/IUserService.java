package vn.hieplp.todo.services;

import vn.hieplp.todo.common.models.UserModel;
import vn.hieplp.todo.common.request.auth.*;
import vn.hieplp.todo.common.response.auth.LoginResponse;
import vn.hieplp.todo.common.response.auth.RegisterResponse;

public interface IUserService {
    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);

    void resetPassword(ResetPasswordRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void changePassword(ChangePasswordRequest request);

    UserModel getUserModel(String userId);

    boolean doesEmailExist(String email);

    boolean isRequestValid(String requestId);
}
