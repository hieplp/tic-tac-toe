package vn.hieplp.todo.config.modules.pattern;

import io.vertx.ext.web.RoutingContext;

public interface IExecutor {
    IExecutor auth();

    IExecutor body();

    IExecutor params();

    IExecutor setRequestClass(Class<?> clazz);

    IExecutor execute(RoutingContext context, IServiceHandler serviceHandler, IResponseHandler responseHandler);

    void ok(RoutingContext context);

    void created(RoutingContext context);
}
