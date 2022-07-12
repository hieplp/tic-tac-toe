package vn.hieplp.todo.common.request.note;

import lombok.Data;
import vn.hieplp.todo.common.annotations.NotNullOrEmpty;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 10/05/2022
 * Time: 16:35
 */
@Data
public class UpdateNoteRequest {
    @NotNullOrEmpty
    private String noteId;
    private String noteTitle;
    private String noteDescription;
    @NotNullOrEmpty
    private String updatedBy;
}
