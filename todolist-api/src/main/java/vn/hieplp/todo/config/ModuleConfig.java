package vn.hieplp.todo.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.WorkerExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.config.ConfigInfo;
import vn.hieplp.todo.common.constant.Constants;
import vn.hieplp.todo.common.utils.Config;
import vn.hieplp.todo.common.utils.Encryption;
import vn.hieplp.todo.common.utils.FileUtil;
import vn.hieplp.todo.config.modules.*;
import vn.hieplp.todo.config.modules.pattern.Executor;
import vn.hieplp.todo.config.modules.pattern.IExecutor;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:14
 */
public class ModuleConfig extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private Vertx vertx;
    private ConfigInfo configInfo;

    public ModuleConfig(Context context) {
        LOGGER.info("Start init module config");
        try {
            configInfo = Config.loadConfig(context.config().encode(), ConfigInfo.class);
            vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(configInfo.getWorkerPoolSize()).setMaxWorkerExecuteTime(configInfo.getWorkerMaxExecuteTime()));
        } catch (Exception e) {
            LOGGER.error("Error on init module config");
            e.printStackTrace();
        }
    }

    @Provides
    @Singleton
    private Vertx getVertx() {
        return this.vertx;
    }

    @Provides
    @Singleton
    public ConfigInfo getConfigInfo() {
        return configInfo;
    }

    @Provides
    @Singleton
    public WorkerExecutor getWorkerExecutor() {
        return Vertx.vertx(new VertxOptions().setWorkerPoolSize(configInfo.getWorkerPoolSize()).setMaxWorkerExecuteTime(configInfo.getWorkerMaxExecuteTime())).createSharedWorkerExecutor("TODO-WORKER");
    }

    @Provides
    @Singleton
    @Named(Constants.Token.PRIVATE_TOKEN)
    public PrivateKey getTokenPrivateKey() throws Exception {
        LOGGER.info("Start get token private key");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(FileUtil.getBytes(getConfigInfo().getTokenConfig().getPrivateKey()));
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    @Provides
    @Singleton
    @Named(Constants.Token.PUBLIC_TOKEN)
    public PublicKey getTokenPublicKey() throws Exception {
        LOGGER.info("Start get token public key");
        return Encryption.getPublicKey(getTokenPrivateKey());
    }

    @Provides
    @Singleton
    @Named(Constants.Token.PRIVATE_AUTH)
    public PrivateKey getAuthPrivateKey() throws Exception {
        LOGGER.info("Start get auth private key");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(FileUtil.getBytes(getConfigInfo().getAuthConfig().getPrivateKey()));
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    @Override
    protected void configure() {
        LOGGER.info("Start loading modules");
        install(new ConsumerModule());
        install(new ServiceModule());
        install(new RepositoryModule());
        install(new HandlerModule());
        install(new JooqModule(configInfo.getDatabaseConfig()));
        bind(IExecutor.class).to(Executor.class);
    }
}
//    openssl genrsa -out token_private_key.pem 2048
//    openssl pkcs8 -topk8 -inform PEM -outform DER -in token_private_key.pem -out auth_private_key.der -nocrypt
//    openssl rsa -in token_private_key.pem -pubout -outform DER -out auth_public_key.der