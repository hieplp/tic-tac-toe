package vn.hieplp.todo.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.exceptions.CommonException;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 15:56
 */
public class Encryption {

    private static final Logger LOGGER = LogManager.getLogger(Encryption.class);

    public static byte[] decrypt(byte[] buffer, PrivateKey privateKey) {
        LOGGER.info("Start decrypt");
        try {
            Cipher rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.DECRYPT_MODE, privateKey);
            return rsa.doFinal(buffer);
        } catch (Exception e) {
            LOGGER.error("Error when decrypt RSA string caused by {}", e.getMessage());
            throw new CommonException.EncryptionException("Error on decrypt password.");
        }
    }

    public static byte[] encrypt(byte[] buffer, PublicKey publicKey) {
        LOGGER.info("Start encrypt");
        try {
            Cipher rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.ENCRYPT_MODE, publicKey);
            return rsa.doFinal(buffer);
        } catch (Exception e) {
            LOGGER.error("Error when encrypt RSA string caused by {}", e.getMessage());
            throw new CommonException.EncryptionException("Error on encrypt password.");
        }
    }

    public static PublicKey getPublicKey(PrivateKey privateKey) {
        LOGGER.info("Start get public key");
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) privateKey;
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaPrivateCrtKey.getModulus(), rsaPrivateCrtKey.getPublicExponent());
            return kf.generatePublic(rsaPublicKeySpec);
        } catch (Exception e) {
            LOGGER.info("Error when get public key from private key: {}", e.getMessage());
            throw new CommonException.EncryptionException("Error when get public key");
        }
    }


    public static byte[] generatePassword(String password, PrivateKey privateKey, byte[] salt) {
        LOGGER.info("Start generate password");
        try {
            byte[] rawPassword = decrypt(Base64.getDecoder().decode(password), privateKey);
            rawPassword = Generator.hash(Converter.toCharArray(rawPassword), salt);
            return rawPassword;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error when generate password: {}", e.getMessage());
            throw new CommonException.EncryptionException("Error on generate password");
        }
    }
}
