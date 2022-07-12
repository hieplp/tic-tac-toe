package vn.hieplp.todo.common.config;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 14:24
 */
public class TokenConfig {
    private String privateKey;
    private String publicKey;
    private String issuer;
    private Integer activeTime;
    private Integer timeToLive;

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getIssuer() {
        return issuer;
    }

    public Integer getActiveTime() {
        return activeTime;
    }

    public Integer getTimeToLive() {
        return timeToLive;
    }
}
