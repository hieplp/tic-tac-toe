package vn.hieplp.todo.common.models;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:46
 */
@Data
public class TaskModel {
    private String taskId;
    private String taskTitle;
    private String taskDescription;
    private Long taskDate;
    private String taskImages;
    private Integer taskStatus;
    private Long createdAt;
}
