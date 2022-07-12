package vn.hieplp.todo.common.config;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:18
 */
public class DatabaseConfig {
    private String driverClassName;
    private String jdbcUrl;
    private Integer maxPoolSize;
    private Integer connectionTimeout;
    private String serverTimezone;
    private String useLegacyDatetimeCode;
    private boolean autoCommit;
    private String username;
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public String getServerTimezone() {
        return serverTimezone;
    }

    public String getUseLegacyDatetimeCode() {
        return useLegacyDatetimeCode;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
