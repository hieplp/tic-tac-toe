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
public class UpdateTaskRequest {
    @NotNullOrEmpty
    private String taskId;
    private String taskTitle;
    private String taskDescription;
    private Long taskDate;
    private String taskImages;
    @NotNullOrEmpty
    private String updatedBy;
}
