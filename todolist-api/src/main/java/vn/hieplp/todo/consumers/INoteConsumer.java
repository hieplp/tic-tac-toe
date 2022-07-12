package vn.hieplp.todo.consumers;

import io.vertx.ext.web.RoutingContext;

public interface INoteConsumer {
    void handleCreateNote(RoutingContext context);

    void handleUpdateNote(RoutingContext context);

    void handleGetNote(RoutingContext context);

    void handleGetNoteList(RoutingContext context);

    void handleDeleteNote(RoutingContext context);

    void handleUpdateNoteStatus(RoutingContext context);

    void handleUpdateNoteIsPinned(RoutingContext context);
}
