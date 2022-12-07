package com.ContentMgtSystem.Blog.util;

import com.ContentMgtSystem.Blog.config.WebConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.time.YearMonth;

public class FileUtil {
    public static void createDirectoryIfNotExist(String directory) {
        final Path path = Paths.get(directory);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Path getImagePath(String fileName) {
        // Create year directory if it doesn't exist
        StringBuilder sb = new StringBuilder();
        sb.append(WebConfig.IMAGE_FILE_BASE);
        sb.append(Year.now().getValue());
        createDirectoryIfNotExist(sb.toString());

        // Create month directory if it doesn't exist
        sb.append("/");
        sb.append(YearMonth.now().getMonthValue());
        createDirectoryIfNotExist(sb.toString());

        // Create and image file path name
        sb.append("/");
        sb.append(fileName);
        return Paths.get(sb.toString());
    }

    public static String getImageUrl(String fileName) {
        String baseUrl = WebConfig.BASE_URL;
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length()-1);
        }
        sb.append(WebConfig.IMAGE_RESOURCE_BASE);
        sb.append(getYearAndMonthUrlFragment());
        sb.append(fileName);
        return sb.toString();
    }

    private static String getYearAndMonthUrlFragment() {
        StringBuilder sb = new StringBuilder();
        sb.append(Year.now().getValue());
        sb.append("/");
        sb.append(YearMonth.now().getMonthValue());
        sb.append("/");
        return sb.toString();
    }
}
