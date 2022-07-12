package vn.hieplp.todo.common.models;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:46
 */
@Data
public class NoteModel {
    private String noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer noteStatus;
    private String userId;
    private Integer isPinned;
    private String createdBy;
    private Long createdAt;
    private String modifiedBy;
    private Long modifiedAt;
}
