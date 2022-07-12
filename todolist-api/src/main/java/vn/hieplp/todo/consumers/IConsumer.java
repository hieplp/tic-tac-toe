package vn.hieplp.todo.consumers;

public interface IConsumer {
    void start();

    IConsumer usingRestful();

    IConsumer enableCorsSupport();
}
