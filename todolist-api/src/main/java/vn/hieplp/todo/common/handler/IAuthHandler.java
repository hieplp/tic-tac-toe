package vn.hieplp.todo.common.handler;

import vn.hieplp.todo.common.models.TokenModel;
import vn.hieplp.todo.common.models.UserModel;
import vn.hieplp.todo.config.modules.pattern.model.HeaderInformation;

public interface IAuthHandler {
    boolean validatePassword(String password, byte[] userPassword, byte[] salt);

    @Deprecated
    String generateEncryptedPassword(String plainPassword);

    TokenModel generateToken(UserModel use);

    void validateToken(String token);

    HeaderInformation getTokenInformation(HeaderInformation headers);
}
