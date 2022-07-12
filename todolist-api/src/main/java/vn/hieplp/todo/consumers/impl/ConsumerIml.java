package vn.hieplp.todo.consumers.impl;

import com.google.inject.Inject;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.config.ConfigInfo;
import vn.hieplp.todo.common.constant.ConfigApi;
import vn.hieplp.todo.common.constant.Constants;
import vn.hieplp.todo.consumers.*;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:01
 */
public class ConsumerIml implements IConsumer {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final Vertx vertx;
    private final ConfigInfo configInfo;
    // Consumers
    private final IUserConsumer userConsumer;
    private final INoteConsumer noteConsumer;
    private final ITaskConsumer taskConsumer;
    private final IFileConsumer fileConsumer;
    private Router router;
    private HttpServer server;

    @Inject
    public ConsumerIml(Vertx vertx,
                       ConfigInfo configInfo,
                       IUserConsumer userConsumer,
                       INoteConsumer noteConsumer,
                       ITaskConsumer taskConsumer,
                       IFileConsumer fileConsumer) {
        this.vertx = vertx;
        this.configInfo = configInfo;
        this.userConsumer = userConsumer;
        this.noteConsumer = noteConsumer;
        this.taskConsumer = taskConsumer;
        this.fileConsumer = fileConsumer;
        this.init();
    }

    @Override
    public void start() {
        LOGGER.info("Starting...");
        server.requestHandler(router);
        server.listen(event -> {
            if (event.succeeded()) {
                LOGGER.info("Listen on port {}", configInfo.getServerConfig().getPort());
            } else {
                LOGGER.error("Listen failed on port {} cause by {} ", configInfo.getServerConfig().getPort(), event.cause().getMessage());
            }
        });
    }

    @Override
    public IConsumer usingRestful() {
        LOGGER.info("Start using restfull");
        // Auth
        router.post(ConfigApi.REGISTER).handler(userConsumer::handleRegister);
        router.post(ConfigApi.LOGIN).handler(userConsumer::handleLogin);
        router.post(ConfigApi.FORGOT_PASSWORD).handler(userConsumer::handleForgotPassword);
        router.post(ConfigApi.RESET_PASSWORD).handler(userConsumer::handleResetPassword);
        router.post(ConfigApi.CHANGE_PASSWORD).handler(userConsumer::handleChangePassword);
        router.get(ConfigApi.CHECK_EMAIL + "/:" + Constants.Path.User.EMAIL).handler(userConsumer::handleCheckEmailExists);
        router.get(ConfigApi.CHECK_REQUEST_VALID + "/:" + Constants.Path.User.REQUEST_ID).handler(userConsumer::handleCheckRequestValid);
        // Note
        router.post(ConfigApi.NOTES).handler(noteConsumer::handleCreateNote);
        router.put(ConfigApi.NOTES + "/:" + Constants.Path.Note.NOTE_ID).handler(noteConsumer::handleUpdateNote);
        router.get(ConfigApi.NOTES + "/:" + Constants.Path.Note.NOTE_ID).handler(noteConsumer::handleGetNote);
        router.get(ConfigApi.NOTES).handler(noteConsumer::handleGetNoteList);
        router.put(ConfigApi.NOTES + "/:" + Constants.Path.Note.NOTE_ID + "/status").handler(noteConsumer::handleUpdateNoteStatus);
        router.put(ConfigApi.NOTES + "/:" + Constants.Path.Note.NOTE_ID + "/pin").handler(noteConsumer::handleUpdateNoteIsPinned);
        router.delete(ConfigApi.NOTES + "/:" + Constants.Path.Note.NOTE_ID).handler(noteConsumer::handleDeleteNote);
        // Task
        router.post(ConfigApi.TASKS).handler(taskConsumer::handleCreateTask);
        router.put(ConfigApi.TASKS).handler(taskConsumer::handleUpdateTask);
        router.get(ConfigApi.TASKS + "/:" + Constants.Path.Task.TASK_ID).handler(taskConsumer::handleGetTask);
        router.get(ConfigApi.TASKS).handler(taskConsumer::handleGetTaskList);
        router.put(ConfigApi.TASKS + "/:" + Constants.Path.Task.TASK_ID + "/status").handler(taskConsumer::handleUpdateTaskStatus);
        router.delete(ConfigApi.TASKS + "/:" + Constants.Path.Task.TASK_ID).handler(taskConsumer::handleDeleteTask);
        router.get(ConfigApi.TASKS + "/status/set").handler(taskConsumer::handleGetTaskStatusSet);
        router.get(ConfigApi.TASKS + "/date/map").handler(taskConsumer::handleGetTaskMap);
        // Upload
        router.post(ConfigApi.UPLOAD_IMAGE)
                .handler(BodyHandler.create()
                        .setUploadsDirectory(configInfo.getStaticConfig().getDirectory()))
                .handler(fileConsumer::handleUploadImage);

        router.route(ConfigApi.UPLOAD_IMAGE + "/*")
                .handler(StaticHandler.create(configInfo.getStaticConfig().getDirectory()).setCachingEnabled(false));

        return this;
    }

    @Override
    public IConsumer enableCorsSupport() {
        LOGGER.info("Enable CORS support");
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.DELETE)
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("token")
                .allowedHeader("Content-Type"));
        return this;
    }

    private void init() {
        router = Router.router(vertx);
        server = vertx.createHttpServer(new HttpServerOptions()
                .setHost(configInfo.getServerConfig().getHost())
                .setPort(configInfo.getServerConfig().getPort()));
    }
}
