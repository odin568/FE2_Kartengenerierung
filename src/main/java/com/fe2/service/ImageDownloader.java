package com.fe2.service;

import com.fe2.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageDownloader {

    @Autowired
    private Configuration configuration;

    public void downloadImage(URL uri, String outputFileNamePrefix) throws IOException {
        Path targetFile = getFullOutputFilePath(outputFileNamePrefix);
        try(InputStream in = uri.openStream()){
            Files.createDirectories(targetFile.getParent());
            Files.copy(in, targetFile, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            // Ensure no old image is there. TODO: Replace by empty image?
            Files.delete(targetFile);
            throw e;
        }
    }

    private Path getFullOutputFilePath(final String outputFileNamePrefix)
    {
        return Paths.get(configuration.getOutputFolder(), outputFileNamePrefix + getFileEnding());
    }

    private String getFileEnding() {

        if (configuration.getOutputFormat().startsWith("png"))
            return ".png";
        if (configuration.getOutputFormat().startsWith("gif"))
            return ".gif";
        if (configuration.getOutputFormat().startsWith("jpg"))
            return ".jpg";

        throw new IllegalArgumentException("Unsupported image format");
    }

}
