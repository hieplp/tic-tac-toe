package com.hieplp.tictactoe.common.handler.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.inject.Inject;
import com.hieplp.tictactoe.common.enums.Constants;
import com.hieplp.tictactoe.common.exception.AuthException;
import com.hieplp.tictactoe.common.handler.IAuthHandler;
import com.hieplp.tictactoe.common.model.UserModel;
import com.hieplp.tictactoe.common.util.DateTimes;
import com.hieplp.tictactoe.common.util.JsonConverter;
import com.hieplp.tictactoe.common.util.States;
import com.hieplp.tictactoe.config.ConfigInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tictactoe.TokenModel;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthHandler implements IAuthHandler {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final ConfigInfo configInfo;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Inject
    public AuthHandler(ConfigInfo configInfo,
                       PrivateKey privateKey,
                       PublicKey publicKey) {
        this.configInfo = configInfo;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    @Override
    public TokenModel generateToken(UserModel user) {
        LOGGER.info("Generate token with user model {}", JsonConverter.toJson(user));
        // Headers
        Map<String, Object> headers = new HashMap<>();
        headers.put(Constants.JWTHeader.USER, user);
        // Expiration time
        Date currentDate = DateTimes.getCurrentDate();
        Date expiredAt = DateTimes.addSeconds(currentDate, configInfo.getTokenConfig().getTimeToLive());
        // JWT builder
        String jwt = Jwts.builder()
                .setHeader(headers)
                .setAudience(user.getUserId())
                .setIssuer(configInfo.getTokenConfig().getIssuer())
                .setExpiration(expiredAt)
                .setIssuedAt(currentDate)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        return TokenModel.newBuilder()
                .setToken(jwt)
                .setExpiredAt(expiredAt.getTime())
                .build();
    }

    @Override
    public UserModel validateToken(String token) {
        LOGGER.info("Validate token {}", token);

        if (States.isNullOrEmpty(token)) {
            throw new AuthException.InvalidTokenException("Token is empty!");
        }

        // Validate token
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);

        // Decode to get token information
        DecodedJWT decodedJWT = JWT.decode(token);

        return JsonConverter.fromMap(decodedJWT.getHeaderClaim(Constants.JWTHeader.USER).asMap(), UserModel.class);
    }
}
