package com.fe2.helper;

import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelper {

    public static Path getFullOutputFilePath(final String outputFolder, final String outputFileNamePrefix, final String outputFormat)
    {
        return Paths.get(outputFolder, outputFileNamePrefix + getFileEnding(outputFormat));
    }

    public static String getFileEnding(final String outputFormat)
    {
        if (outputFormat.startsWith("png"))
            return ".png";
        if (outputFormat.startsWith("gif"))
            return ".gif";
        if (outputFormat.startsWith("jpg"))
            return ".jpg";

        throw new IllegalArgumentException("Unsupported image format");
    }

    public static MediaType getMediaType(final String outputFormat)
    {
        if (outputFormat.startsWith("png"))
            return MediaType.IMAGE_PNG;
        if (outputFormat.startsWith("gif"))
            return MediaType.IMAGE_GIF;
        if (outputFormat.startsWith("jpg"))
            return MediaType.IMAGE_JPEG;

        throw new IllegalArgumentException("Unsupported image format");
    }
}
