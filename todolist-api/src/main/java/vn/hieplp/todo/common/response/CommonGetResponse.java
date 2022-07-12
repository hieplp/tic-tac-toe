package vn.hieplp.todo.common.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 10/5/2022
 * Time: 22:3
 **/
@Data
@Builder
public class CommonGetResponse {
    public List<?> list;
    public int total;
}
