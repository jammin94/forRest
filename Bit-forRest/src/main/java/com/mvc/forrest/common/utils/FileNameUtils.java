package com.mvc.forrest.common.utils;

import java.util.UUID;

public class FileNameUtils {

    public static String fileNameConvert(String fileName) {
        StringBuilder builder = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        String extension = getExtension(fileName);

        builder.append(uuid).append(".").append(extension);

        return builder.toString();
    }

    public static String getRandomString() {
        StringBuilder builder = new StringBuilder();
        UUID uuid = UUID.randomUUID();

        builder.append(uuid);

        return builder.toString();
    }
    
    
    static String getExtension(String fileName) {
        int pos = fileName.lastIndexOf(".");

        return fileName.substring(pos + 1);
    }

    public static String getFileName(String path) {
        int idx = path.lastIndexOf("/");

        return path.substring(idx + 1);
    }

}