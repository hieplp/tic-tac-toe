package vn.hieplp.todo.common.request;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 10/5/2022
 * Time: 22:0
 **/
@Data
public class CommonGetRequest {
    private int from;
    private int limit;
    // Order
    private String order;
    private String by;
    // Filter
    private String filterBy;
    private String filterValue;
    // Search
    private String searchBy;
    private String searchValue;

    private String userId;
}
