package vn.hieplp.todo.common.request.note;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 26/05/2022
 * Time: 16:01
 */
@Data
public class UpdateNoteIsPinnedRequest {
    private String noteId;
    private Integer isPinned;
    private String updatedBy;
}
