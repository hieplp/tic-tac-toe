package vn.hieplp.todo.consumers;

import io.vertx.ext.web.RoutingContext;

public interface IFileConsumer {
    void handleUploadImage(RoutingContext context);
}
