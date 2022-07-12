package vn.hieplp.todo.common.response;

import lombok.Builder;
import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:35
 */
@Data
@Builder
public class RestfulCommonResponse {
    protected String code;
    protected Object data;
    protected String messages;

}
