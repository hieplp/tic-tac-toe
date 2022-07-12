package vn.hieplp.todo.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.PublicKey;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 09/05/2022
 * Time: 11:35
 */
public class TokenUtil {
    public static Jws<Claims> parseJwt(String jwtString, PublicKey publicKey) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwtString);
    }
}
