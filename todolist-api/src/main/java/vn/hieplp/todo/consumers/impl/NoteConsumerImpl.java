package vn.hieplp.todo.consumers.impl;

import com.google.inject.Inject;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.app.App;
import vn.hieplp.todo.common.constant.Constants;
import vn.hieplp.todo.common.handler.IValidationHandler;
import vn.hieplp.todo.common.request.note.*;
import vn.hieplp.todo.config.modules.pattern.IExecutor;
import vn.hieplp.todo.consumers.INoteConsumer;
import vn.hieplp.todo.services.INoteService;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 11:18
 */
public class NoteConsumerImpl implements INoteConsumer {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final INoteService noteService;
    private final IValidationHandler validationHandler;

    @Inject
    public NoteConsumerImpl(INoteService noteService, IValidationHandler validationHandler) {
        this.noteService = noteService;
        this.validationHandler = validationHandler;
    }

    @Override
    public void handleCreateNote(RoutingContext context) {
        LOGGER.info("Handle create note");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .body()
                .setRequestClass(CreateNoteRequest.class)
                .execute(context, commonRequest -> {
                    validationHandler.checkNotNullWithAnnotation(commonRequest.getRequest());
                    CreateNoteRequest request = (CreateNoteRequest) commonRequest.getRequest();
                    request.setUserId(commonRequest.getHeaders().getUserId());
                    return noteService.createNote(request);
                }, executor::created);
    }

    @Override
    public void handleUpdateNote(RoutingContext context) {
        LOGGER.info("Handle update note");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .body()
                .setRequestClass(UpdateNoteRequest.class)
                .execute(context, commonRequest -> {
                    UpdateNoteRequest request = (UpdateNoteRequest) commonRequest.getRequest();
                    request.setUpdatedBy(commonRequest.getHeaders().getUserId());
                    request.setNoteId(context.pathParam(Constants.Path.Note.NOTE_ID));
                    validationHandler.checkNotNullWithAnnotation(commonRequest.getRequest());
                    return noteService.updateNote(request);
                }, executor::ok);
    }

    @Override
    public void handleGetNote(RoutingContext context) {
        LOGGER.info("Handle get note");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .execute(context, commonRequest -> noteService.getNoteModel(context.pathParam(Constants.Path.Note.NOTE_ID), commonRequest.getHeaders().getUserId()), executor::ok);
    }

    @Override
    public void handleGetNoteList(RoutingContext context) {
        LOGGER.info("Handle get note list");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .setRequestClass(GetNoteListRequest.class)
                .execute(context, commonRequest -> {
                    GetNoteListRequest request = (GetNoteListRequest) commonRequest.getRequest();
                    request.setUserId(commonRequest.getHeaders().getUserId());
                    return noteService.getNoteList(request);
                }, executor::ok);
    }

    @Override
    public void handleDeleteNote(RoutingContext context) {
        LOGGER.info("Handle delete note");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .execute(context, commonRequest -> noteService.deleteNote(context.pathParam(Constants.Path.Note.NOTE_ID), commonRequest.getHeaders().getUserId()), executor::ok);

    }

    @Override
    public void handleUpdateNoteStatus(RoutingContext context) {
        LOGGER.info("Handle update note status");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .body()
                .setRequestClass(UpdateNoteStatusRequest.class)
                .execute(context, commonRequest -> {
                    UpdateNoteStatusRequest request = (UpdateNoteStatusRequest) commonRequest.getRequest();
                    request.setUpdatedBy(commonRequest.getHeaders().getUserId());
                    request.setNoteId(context.pathParam(Constants.Path.Note.NOTE_ID));
                    validationHandler.checkNullAll(request);
                    return noteService.updateNoteStatus(request);
                }, executor::ok);
    }

    @Override
    public void handleUpdateNoteIsPinned(RoutingContext context) {
        LOGGER.info("Handle update note isPinned");
        IExecutor executor = App.injector.getInstance(IExecutor.class);
        executor
                .auth()
                .body()
                .setRequestClass(UpdateNoteIsPinnedRequest.class)
                .execute(context, commonRequest -> {
                    UpdateNoteIsPinnedRequest request = (UpdateNoteIsPinnedRequest) commonRequest.getRequest();
                    request.setNoteId(context.pathParam(Constants.Path.Note.NOTE_ID));
                    request.setUpdatedBy(commonRequest.getHeaders().getUserId());
                    validationHandler.checkNullAll(request);
                    return noteService.updateNoteIsPinned(request);
                }, executor::ok);

    }
}
