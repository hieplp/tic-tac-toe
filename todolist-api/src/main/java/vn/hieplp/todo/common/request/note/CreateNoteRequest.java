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
public class CreateNoteRequest {
    @NotNullOrEmpty
    private String noteTitle;
    private String noteDescription;
    private String userId;
    private Integer isPinned;
}
