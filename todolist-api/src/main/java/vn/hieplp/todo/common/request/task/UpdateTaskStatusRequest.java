package vn.hieplp.todo.common.request.task;

import lombok.Builder;
import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 11/05/2022
 * Time: 10:36
 */
@Data
@Builder
public class UpdateTaskStatusRequest {
    private String taskId;
    private Integer taskStatus;
    private String updatedBy;
}
