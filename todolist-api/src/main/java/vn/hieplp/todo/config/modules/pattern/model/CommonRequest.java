package vn.hieplp.todo.config.modules.pattern.model;

import lombok.Builder;
import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 06/05/2022
 * Time: 15:31
 */
@Data
@Builder
public class CommonRequest {
    private Object request;
    private HeaderInformation headers;
}
