package vn.hieplp.todo.common.constant;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 1/5/2022
 * Time: 21:14
 **/
public class ConfigApi {
    public static final String USER = "/user";
    // Note
    public static final String NOTES = "/notes";
    // Task
    public static final String TASKS = "/tasks";
    // File
    public static final String UPLOAD = "/uploads";
    public static final String UPLOAD_IMAGE = UPLOAD + "/images";
    // Auth
    private static final String AUTH = "/auth";
    public static final String REGISTER = AUTH + "/register";
    public static final String LOGIN = AUTH + "/login";
    public static final String FORGOT_PASSWORD = AUTH + "/forgot-password";
    public static final String RESET_PASSWORD = AUTH + "/reset-password";
    public static final String CHANGE_PASSWORD = AUTH + "/change-password";
    public static final String CHECK_EMAIL = AUTH + "/check-email";
    public static final String CHECK_REQUEST_VALID = AUTH + "/check-request";
}
