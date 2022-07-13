package com.hieplp.tictactoe.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 20/06/2022
 * Time: 10:03
 */
public class FileUtil {
    public static String getFilePathFromUserDir(String fileName) {
        return System.getProperty("user.dir") + fileName;
    }

    public static byte[] getBytes(String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(getFilePathFromUserDir(fileName)));
    }

    public static String getAllLines(String fileName) throws IOException {
        return Files.readString(Paths.get(getFilePathFromUserDir(fileName)));
    }
}
