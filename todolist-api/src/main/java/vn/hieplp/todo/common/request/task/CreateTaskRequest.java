package vn.hieplp.todo.common.request.task;

import lombok.Data;
import vn.hieplp.todo.common.annotations.NotNullOrEmpty;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 11/05/2022
 * Time: 10:35
 */
@Data
public class CreateTaskRequest {
    @NotNullOrEmpty
    private String taskTitle;
    private String taskDescription;
    @NotNullOrEmpty
    private Long taskDate;
    private String taskImages;
    @NotNullOrEmpty
    private String createdBy;
}
