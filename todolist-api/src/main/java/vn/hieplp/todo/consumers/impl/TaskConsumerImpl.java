package vn.hieplp.todo.consumers.impl;

import com.google.inject.Inject;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.app.App;
import vn.hieplp.todo.common.constant.Constants;
import vn.hieplp.todo.common.handler.IValidationHandler;
import vn.hieplp.todo.common.request.task.CreateTaskRequest;
import vn.hieplp.todo.common.request.task.GetTaskListRequest;
import vn.hieplp.todo.common.request.task.UpdateTaskRequest;
import vn.hieplp.todo.common.request.task.UpdateTaskStatusRequest;
import vn.hieplp.todo.config.modules.pattern.IExecutor;
import vn.hieplp.todo.consumers.ITaskConsumer;
import vn.hieplp.todo.services.ITaskService;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 11:17
 */
public class TaskConsumerImpl implements ITaskConsumer {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final ITaskService taskService;
    private final IValidationHandler validationHandler;

    @Inject
    public TaskConsumerImpl(ITaskService taskService, IValidationHandler validationHandler) {
        this.taskService = taskService;
        this.validationHandler = validationHandler;
    }

    @Override
    public void handleCreateTask(RoutingContext context) {
        LOGGER.info("Handle create task");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .body()
                .setRequestClass(CreateTaskRequest.class)
                .execute(context, commonRequest -> {
                    CreateTaskRequest request = (CreateTaskRequest) commonRequest.getRequest();
                    request.setCreatedBy(commonRequest.getHeaders().getUserId());
                    validationHandler.checkNotNullWithAnnotation(request);
                    return taskService.createTask(request);
                }, executor::created);
    }

    @Override
    public void handleUpdateTask(RoutingContext context) {
        LOGGER.info("Handle update task");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .body()
                .setRequestClass(UpdateTaskRequest.class)
                .execute(context, commonRequest -> {
                    UpdateTaskRequest request = (UpdateTaskRequest) commonRequest.getRequest();
                    request.setUpdatedBy(commonRequest.getHeaders().getUserId());
                    validationHandler.checkNotNullWithAnnotation(request);
                    return taskService.updateTask(request);
                }, executor::ok);
    }

    @Override
    public void handleUpdateTaskStatus(RoutingContext context) {
        LOGGER.info("Handle update task status");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .body()
                .setRequestClass(UpdateTaskStatusRequest.class)
                .execute(context, commonRequest -> {
                    UpdateTaskStatusRequest request = (UpdateTaskStatusRequest) commonRequest.getRequest();
                    request.setTaskId(context.pathParam(Constants.Path.Task.TASK_ID));
                    request.setUpdatedBy(commonRequest.getHeaders().getUserId());
                    validationHandler.checkNullAll(request);
                    return taskService.updateTaskStatus(request);
                }, executor::ok);
    }

    @Override
    public void handleGetTask(RoutingContext context) {
        LOGGER.info("Handle get task");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .execute(context, commonRequest -> {
                    String taskId = context.pathParam(Constants.Path.Task.TASK_ID);
                    return taskService.getTaskModel(taskId, commonRequest.getHeaders().getUserId());
                }, executor::ok);
    }

    @Override
    public void handleGetTaskList(RoutingContext context) {
        LOGGER.info("Handle get task list");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .setRequestClass(GetTaskListRequest.class)
                .execute(context, commonRequest -> taskService.getTaskList((GetTaskListRequest) commonRequest.getRequest()), executor::ok);
    }

    @Override
    public void handleDeleteTask(RoutingContext context) {
        LOGGER.info("Handle delete task");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .execute(context, commonRequest -> taskService.deleteTask(context.pathParam(Constants.Path.Task.TASK_ID), commonRequest.getHeaders().getUserId()), executor::ok);
    }

    @Override
    public void handleGetTaskStatusSet(RoutingContext context) {
        LOGGER.info("Handle get task status set");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .execute(context, commonRequest -> {
                    Long fromDate = Long.valueOf(context.queryParams().get("fromDate"));
                    Long toDate = Long.valueOf(context.queryParams().get("toDate"));
                    return taskService.getTaskStatusSetByDate(fromDate, toDate, commonRequest.getHeaders().getUserId());
                }, executor::ok);
    }

    @Override
    public void handleGetTaskMap(RoutingContext context) {
        LOGGER.info("Handle get task map");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .execute(context, commonRequest -> {
                    Long fromDate = Long.valueOf(context.queryParams().get("fromDate"));
                    Long toDate = Long.valueOf(context.queryParams().get("toDate"));
                    return taskService.getTaskMap(fromDate, toDate, commonRequest.getHeaders().getUserId());
                }, executor::ok);
    }
}
