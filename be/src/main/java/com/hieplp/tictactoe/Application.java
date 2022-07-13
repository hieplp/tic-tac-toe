package com.hieplp.tictactoe;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hieplp.tictactoe.common.exception.CommonException;
import com.hieplp.tictactoe.common.grpc.GRPCServer;
import com.hieplp.tictactoe.common.grpc.IGRPCServer;
import com.hieplp.tictactoe.common.util.FileUtil;
import com.hieplp.tictactoe.common.util.JsonConverter;
import com.hieplp.tictactoe.config.ConfigInfo;
import com.hieplp.tictactoe.config.ModuleConfig;
import com.hieplp.tictactoe.interceptor.TokenInterceptor;
import com.hieplp.tictactoe.service.AuthService;
import com.hieplp.tictactoe.service.AuthTokenService;
import com.hieplp.tictactoe.service.GameService;
import com.hieplp.tictactoe.service.HelloService;
import io.grpc.BindableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 14/06/2022
 * Time: 13:38
 */
public class Application {

    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static Injector injector;

    public static void main(String[] args) throws IOException, InterruptedException {
        LOGGER.info("Start tic-tac-toe service...");
        if (args.length == 0) {
            throw new CommonException.InvalidConfig("Not found config");
        }

        // Config
        String configFile = args[0];
        ConfigInfo configInfo = JsonConverter.fromJson(FileUtil.getAllLines(configFile), ConfigInfo.class);

        injector = Guice.createInjector(new ModuleConfig(configInfo));

        IGRPCServer server = new GRPCServer(configInfo.getGrpcConfig());

        List<BindableService> services = new ArrayList<>();
        services.add(injector.getInstance(HelloService.class));
        services.add(injector.getInstance(AuthService.class));

        LOGGER.info("Server started successfully with port {}", configInfo.getGrpcConfig().getPort());
        TokenInterceptor tokenInterceptor = injector.getInstance(TokenInterceptor.class);
        server
                .addServices(services)
                .addService(injector.getInstance(AuthTokenService.class), tokenInterceptor)
                .addService(injector.getInstance(GameService.class), tokenInterceptor)
                .start()
                .blockUntilShutdown();
    }
}
