package com.hieplp.tictactoe.common.handler;

import com.hieplp.tictactoe.common.model.UserModel;
import tictactoe.TokenModel;

public interface IAuthHandler {
    TokenModel generateToken(UserModel user);

    UserModel validateToken(String token);
}
