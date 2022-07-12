package vn.hieplp.todo.common.response;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:38
 */
public class RestfulFailureResponse extends RestfulCommonResponse {
    RestfulFailureResponse(String code, Object data, String messages) {
        super(code, data, messages);
    }
}
