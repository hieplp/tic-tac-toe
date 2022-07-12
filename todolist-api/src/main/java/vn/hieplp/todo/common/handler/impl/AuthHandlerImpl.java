package vn.hieplp.todo.common.handler.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.config.ConfigInfo;
import vn.hieplp.todo.common.constant.Constants;
import vn.hieplp.todo.common.exceptions.AuthException;
import vn.hieplp.todo.common.handler.IAuthHandler;
import vn.hieplp.todo.common.models.TokenModel;
import vn.hieplp.todo.common.models.UserModel;
import vn.hieplp.todo.common.utils.DateTimes;
import vn.hieplp.todo.common.utils.Encryption;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.common.utils.States;
import vn.hieplp.todo.config.modules.pattern.model.HeaderInformation;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 15:29
 */
public class AuthHandlerImpl implements IAuthHandler {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final PrivateKey tokenPrivateKey;
    private final PublicKey tokenPublicKey;
    private final PrivateKey authPrivateKey;
    private final ConfigInfo configInfo;

    @Inject
    public AuthHandlerImpl(@Named(Constants.Token.PRIVATE_TOKEN) PrivateKey tokenPrivateKey,
                           @Named(Constants.Token.PUBLIC_TOKEN) PublicKey tokenPublicKey,
                           @Named(Constants.Token.PRIVATE_AUTH) PrivateKey authPrivateKey,
                           ConfigInfo configInfo) {
        this.tokenPrivateKey = tokenPrivateKey;
        this.tokenPublicKey = tokenPublicKey;
        this.authPrivateKey = authPrivateKey;
        this.configInfo = configInfo;
    }

    @Override
    public boolean validatePassword(String inputPassword, byte[] userPassword, byte[] salt) {
        LOGGER.info("Start validate password");
        byte[] rawPassword = new byte[0];
        try {
            rawPassword = Encryption.generatePassword(inputPassword, authPrivateKey, salt);
            return Arrays.equals(rawPassword, userPassword);
        } catch (Exception e) {
            LOGGER.error("Error when validate password caused by {}", e.getMessage());
            return false;
        } finally {
            // Clear password in memory for security
            Arrays.fill(rawPassword, Byte.MIN_VALUE);
            Arrays.fill(userPassword, Byte.MIN_VALUE);
        }
    }

    @Override
    @Deprecated
    public String generateEncryptedPassword(String plainPassword) {
        try {
            byte[] bytes = Encryption.encrypt(plainPassword.getBytes(), Encryption.getPublicKey(authPrivateKey));
            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            LOGGER.error("Error when generate encrypted password: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public TokenModel generateToken(UserModel user) {
        LOGGER.info("Start generate token");
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
                .setExpiration(expiredAt)
                .setIssuedAt(currentDate)
                .signWith(SignatureAlgorithm.RS256, tokenPrivateKey)
                .compact();

        return TokenModel
                .builder()
                .token(jwt)
                .expiredAt(expiredAt.getTime())
                .build();
    }

    @Override
    public void validateToken(String token) {
        LOGGER.info("Validate token {}", token);

        if (States.isNullOrEmpty(token)) {
            throw new AuthException.InvalidTokenException("Token is empty");
        }

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) tokenPublicKey, (RSAPrivateKey) tokenPrivateKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        verifier.verify(token);
    }

    @Override
    public HeaderInformation getTokenInformation(HeaderInformation headers) {
        LOGGER.info("Get token information");
        DecodedJWT decodedJWT = JWT.decode(headers.getToken());
        UserModel user = JsonConverter.fromMap(decodedJWT.getHeaderClaim(Constants.JWTHeader.USER).asMap(), UserModel.class);
        headers.setUserId(user.getUserId());
        return headers;
    }
}
