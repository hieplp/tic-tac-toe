package vn.hieplp.todo.common.config;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 09/05/2022
 * Time: 17:45
 */
@Data
public class EmailConfig {
    private String host;
    private String port;
    private String from;
    private String username;
    private String password;
    private boolean isDebug;
}
