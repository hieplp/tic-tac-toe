package vn.hieplp.todo.common.request.note;

import lombok.Builder;
import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 10/5/2022
 * Time: 21:9
 **/
@Data
@Builder
public class UpdateNoteStatusRequest {
    private String noteId;
    private Integer noteStatus;
    private String updatedBy;
}
