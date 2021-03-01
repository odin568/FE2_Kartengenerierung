package com.fe2.service;

import com.fe2.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageDownloader {

    @Autowired
    private Configuration configuration;

    public void downloadImage(URL uri) throws IOException {
        download(uri, configuration.getOutputFile());
    }

    private void download(URL uri, String outputFile) throws IOException {
        String format = "png"; //Default
        String query = uri.getQuery().toLowerCase();
        if (query.contains("&format=jpg"))
            format = "jpg";
        else if (query.contains("&format=gif"))
            format = "gif";

        if (!outputFile.endsWith("." + format)) {
            throw new IllegalArgumentException("File ending does not match file type (" + format + ")!");
        }

        try(InputStream in = uri.openStream()){
            Files.copy(in, Paths.get(outputFile), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            // Ensure no old image is there. TODO: Replace by empty image?
            Files.delete(Paths.get(outputFile));
            throw e;
        }
    }

}
