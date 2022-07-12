package vn.hieplp.todo.consumers;

import io.vertx.ext.web.RoutingContext;

public interface IUserConsumer {
    void handleRegister(RoutingContext context);

    void handleLogin(RoutingContext context);

    void handleForgotPassword(RoutingContext context);

    void handleResetPassword(RoutingContext context);

    void handleChangePassword(RoutingContext context);

    void handleCheckEmailExists(RoutingContext context);

    void handleCheckRequestValid(RoutingContext context);
}
