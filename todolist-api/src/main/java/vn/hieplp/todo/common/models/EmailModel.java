package vn.hieplp.todo.common.models;

import lombok.Builder;
import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 9/5/2022
 * Time: 21:10
 **/
@Data
@Builder
public class EmailModel {
    private String sendTo;
    private String title;
    private String content;
}
