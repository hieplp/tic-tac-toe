package com.hieplp.tictactoe.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hieplp.tictactoe.common.model.JoinedGameModel;
import com.hieplp.tictactoe.common.model.UserModel;
import com.hieplp.tictactoe.common.util.FileUtil;
import com.hieplp.tictactoe.config.module.HandlerModule;
import com.hieplp.tictactoe.config.module.InterceptorModule;
import com.hieplp.tictactoe.config.module.ServiceModule;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tictactoe.GameModel;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 16/06/2022
 * Time: 09:54
 */
public class ModuleConfig extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final ConfigInfo configInfo;

    public ModuleConfig(ConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    @Provides
    @Singleton
    public ConfigInfo getConfigInfo() {
        return this.configInfo;
    }

    @Provides
    @Singleton
    public Map<String, UserModel> getUserMap() {
        return new HashMap<>();
    }

    @Provides
    @Singleton
    public Map<String, GameModel> getGameMap() {
        return new HashMap<>();
    }

    @Provides
    @Singleton
    public Map<String, Map<String, StreamObserver<GameModel>>> getGameStream() {
        return new HashMap<>();
    }

    @Provides
    @Singleton
    public Map<StreamObserver<GameModel>, JoinedGameModel> getJoinedGameMap() {
        // When user closes connection
        // -> Remove all response observer from all games
        return new HashMap<>();
    }

    @Provides
    @Singleton
    public PrivateKey getPrivateKey() throws Exception {
        LOGGER.info("Get token private key");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(FileUtil.getBytes(configInfo.getTokenConfig().getPrivateKey()));
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    @Provides
    @Singleton
    public PublicKey getPublicKey() throws Exception {
        LOGGER.info("Get token private key");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) getPrivateKey();
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaPrivateCrtKey.getModulus(), rsaPrivateCrtKey.getPublicExponent());
        return kf.generatePublic(rsaPublicKeySpec);
    }

    @Override
    protected void configure() {
        LOGGER.info("Start binding values");
        install(new ServiceModule());
        install(new HandlerModule());
        install(new InterceptorModule());
    }
}
