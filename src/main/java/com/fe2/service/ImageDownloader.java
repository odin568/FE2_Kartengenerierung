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

    public void downloadImage(URL uri, String outputFile) throws IOException {
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
