package vn.hieplp.todo.config.modules.pattern;

import io.vertx.ext.web.RoutingContext;

@FunctionalInterface
public interface IResponseHandler {
    void end(RoutingContext context);
}
