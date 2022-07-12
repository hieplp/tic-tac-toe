package vn.hieplp.todo.repositories.sources;

import vn.hieplp.todo.common.models.NoteModel;
import vn.hieplp.todo.common.request.note.GetNoteListRequest;
import vn.hieplp.todo.common.response.CommonGetResponse;
import vn.hieplp.todo.repositories.base.IBaseRepository;
import vn.hieplp.todo.repositories.generates.tables.records.NoteRecord;

public interface INoteRepository extends IBaseRepository {
    NoteModel getNoteModel(String noteId);

    NoteModel getNoteModel(String noteId, String userId);

    NoteRecord getNoteRecord(String noteId);

    NoteRecord getNoteRecord(String noteId, String userId);

    CommonGetResponse getNoteList(GetNoteListRequest request);
}
