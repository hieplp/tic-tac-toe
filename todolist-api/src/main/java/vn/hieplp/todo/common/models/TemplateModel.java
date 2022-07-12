package vn.hieplp.todo.common.models;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 06/05/2022
 * Time: 10:55
 */
@Data
public class TemplateModel {
    private String templateName;
    private Integer sendVia;
    private String title;
    private String content;
}
