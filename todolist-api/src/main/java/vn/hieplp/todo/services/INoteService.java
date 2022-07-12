package vn.hieplp.todo.services;

import vn.hieplp.todo.common.models.NoteModel;
import vn.hieplp.todo.common.request.note.*;
import vn.hieplp.todo.common.response.CommonGetResponse;

public interface INoteService {
    NoteModel createNote(CreateNoteRequest request);

    NoteModel updateNote(UpdateNoteRequest request);

    NoteModel updateNoteStatus(UpdateNoteStatusRequest request);

    NoteModel updateNoteIsPinned(UpdateNoteIsPinnedRequest request);

    NoteModel getNoteModel(String noteId);

    NoteModel getNoteModel(String noteId, String userId);

    CommonGetResponse getNoteList(GetNoteListRequest request);

    NoteModel deleteNote(String noteId, String userId);
}
