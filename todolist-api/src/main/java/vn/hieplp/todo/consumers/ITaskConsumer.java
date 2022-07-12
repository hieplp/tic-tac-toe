package vn.hieplp.todo.consumers;

import io.vertx.ext.web.RoutingContext;

public interface ITaskConsumer {
    void handleCreateTask(RoutingContext context);

    void handleUpdateTask(RoutingContext context);

    void handleUpdateTaskStatus(RoutingContext context);

    void handleGetTask(RoutingContext context);

    void handleGetTaskList(RoutingContext context);

    void handleDeleteTask(RoutingContext context);

    void handleGetTaskStatusSet(RoutingContext context);

    void handleGetTaskMap(RoutingContext context);
}
