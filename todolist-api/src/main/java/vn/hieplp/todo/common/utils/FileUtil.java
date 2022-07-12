package vn.hieplp.todo.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 1/5/2022
 * Time: 21:45
 **/


public class FileUtil {
    public static File getFileFromUserDir(String fileName) {
        return new File(getFilePathFromUserDir(fileName));
    }

    public static String getFilePathFromUserDir(String fileName) {
        return System.getProperty("user.dir") + fileName;
    }

    public static byte[] getBytes(String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(getFilePathFromUserDir(fileName)));
    }
}
