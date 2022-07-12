package vn.hieplp.todo.services.impl;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.constant.StaticEnum;
import vn.hieplp.todo.common.models.NoteModel;
import vn.hieplp.todo.common.request.note.*;
import vn.hieplp.todo.common.response.CommonGetResponse;
import vn.hieplp.todo.common.utils.DateTimes;
import vn.hieplp.todo.common.utils.Generator;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.repositories.generates.tables.records.NoteRecord;
import vn.hieplp.todo.repositories.sources.INoteRepository;
import vn.hieplp.todo.services.INoteService;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 11:11
 */
public class NoteServiceImpl implements INoteService {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final INoteRepository noteRepository;

    @Inject
    public NoteServiceImpl(INoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public NoteModel createNote(CreateNoteRequest request) {
        LOGGER.info("Create note with request {}", JsonConverter.toJson(request));

        NoteRecord noteRecord = new NoteRecord();
        noteRecord.from(request);
        noteRecord.setNoteid(Generator.generateRandomString(10));
        noteRecord.setCreatedat(DateTimes.getCurrentLocalDateTime());
        noteRecord.setCreatedby(request.getUserId());
        noteRecord.setModifiedat(DateTimes.getCurrentLocalDateTime());
        noteRecord.setModifiedby(request.getUserId());
        noteRepository.save(noteRecord);

        return this.getNoteModel(noteRecord.getNoteid());
    }

    @Override
    public NoteModel updateNote(UpdateNoteRequest request) {
        LOGGER.info("Update note with request {}", JsonConverter.toJson(request));
        // Get only own note
        NoteRecord noteRecord = noteRepository.getNoteRecord(request.getNoteId(), request.getUpdatedBy());
        // Update note
        noteRecord.from(request);
        noteRecord.setModifiedby(request.getUpdatedBy());
        noteRecord.setModifiedat(DateTimes.getCurrentLocalDateTime());
        noteRepository.updateNotNull(noteRecord);

        return this.getNoteModel(request.getNoteId());
    }

    @Override
    public NoteModel updateNoteStatus(UpdateNoteStatusRequest request) {
        LOGGER.info("Update note status with request {}", JsonConverter.toJson(request));
        // Validation note status
        StaticEnum.NoteStatus noteStatus = StaticEnum.NoteStatus.safeValueOf(request.getNoteStatus());
        // Update note status
        NoteRecord noteRecord = noteRepository.getNoteRecord(request.getNoteId(), request.getUpdatedBy());
        noteRecord.setNotestatus(noteStatus.getStatus());
        noteRecord.setModifiedby(request.getUpdatedBy());
        noteRecord.setModifiedat(DateTimes.getCurrentLocalDateTime());
        noteRepository.updateNotNull(noteRecord);

        return this.getNoteModel(request.getNoteId());
    }

    @Override
    public NoteModel updateNoteIsPinned(UpdateNoteIsPinnedRequest request) {
        LOGGER.info("Update note isPinned with request {}", JsonConverter.toJson(request));
        // Validate note isPinned
        StaticEnum.NoteIsPinned isPinned = StaticEnum.NoteIsPinned.safeValueOf(request.getIsPinned());
        // Update note isPinned
        NoteRecord noteRecord = noteRepository.getNoteRecord(request.getNoteId(), request.getUpdatedBy());
        noteRecord.setIspinned(isPinned.getStatus());
        noteRecord.setModifiedby(request.getUpdatedBy());
        noteRecord.setModifiedat(DateTimes.getCurrentLocalDateTime());
        noteRepository.updateNotNull(noteRecord);

        return this.getNoteModel(noteRecord.getNoteid());
    }

    @Override
    public NoteModel getNoteModel(String noteId) {
        LOGGER.info("Get note with noteId {}", noteId);
        return noteRepository.getNoteModel(noteId);
    }

    @Override
    public NoteModel getNoteModel(String noteId, String userId) {
        LOGGER.info("Get note with noteId {} and userId {}", noteId, userId);
        return noteRepository.getNoteModel(noteId, userId);
    }

    @Override
    public CommonGetResponse getNoteList(GetNoteListRequest request) {
        LOGGER.info("Get note list with request {}", JsonConverter.toJson(request));
        return noteRepository.getNoteList(request);
    }

    @Override
    public NoteModel deleteNote(String noteId, String userId) {
        LOGGER.info("Delete note with noteId {} and userId {}", noteId, userId);
        UpdateNoteStatusRequest request = UpdateNoteStatusRequest.builder()
                .noteId(noteId)
                .noteStatus(StaticEnum.NoteStatus.DELETED.getStatus())
                .updatedBy(userId)
                .build();

        return this.updateNoteStatus(request);
    }
}
