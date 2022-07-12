package vn.hieplp.todo.config.modules.pattern;

import vn.hieplp.todo.config.modules.pattern.model.CommonRequest;

@FunctionalInterface
public interface IServiceHandler {
    Object handle(CommonRequest request);
}

