package vn.hieplp.todo.common.response;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:38
 */
public class RestfulSuccessResponse extends RestfulCommonResponse {
    RestfulSuccessResponse(String code, Object data, String messages) {
        super(code, data, messages);
    }
}
