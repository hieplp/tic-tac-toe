package vn.hieplp.todo.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 15:58
 */
public class Generator {
    private static final Logger LOGGER = LogManager.getLogger(Generator.class);

    private static final String CHAR_LIST = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Integer SALT_SIZE = 64;
    private static final Integer ITERATION_COUNT = 65536;
    private static final Integer KEY_LENGTH = 64 * 8;

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        SecureRandom rnd = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(CHAR_LIST.charAt(rnd.nextInt(CHAR_LIST.length())));
        }
        return sb.toString();
    }


    /**
     * Generate salt with SHA1PRNG
     *
     * @return salt with SHA1PRNG
     * @throws NoSuchAlgorithmException - SHA1PRNG
     */
    public static byte[] generateSalt() throws NoSuchAlgorithmException {
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        final Random sr = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Convert byte array to hex string
     *
     * @param array - byte array
     * @return hex string
     */
    public static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * Hash password with char array password
     *
     * @param password - char array password
     * @param salt     - salt
     * @return Hashed password
     */
    public static byte[] hash(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     * Hash password with string password
     *
     * @param password - String password
     * @param salt     - salt
     * @return Hashed password
     */
    public static byte[] hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return hash(password.toCharArray(), salt);
    }
}
