package com.fe2.service;

import com.fe2.configuration.Configuration;
import com.fe2.helper.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class MapGenerator {

    @Autowired
    private UrlBuilder builder;

    @Autowired
    private Configuration configuration;

    public ResponseEntity<Object> generateMap(final String endpoint, final double lat, final double lng, final boolean store)
    {
        Path outputFile = FileHelper.getFullOutputFilePath(configuration.getOutputFolder(), endpoint, configuration.getOutputFormat());

        // Ensure that no old file is lying around if we are in store mode.
        try {
            if (store && Files.exists(outputFile))
                Files.delete(outputFile);
        }
        catch (IOException e) {
            return generateErrorResponse("ERROR: Unable to delete image from previous execution!");
        }

        // Germany: Latitude from 47.40724 to 54.9079 and longitude from 5.98815 to 14.98853.
        if (lat < lng || lat < 47 || lat > 54 || lng < 5 || lng > 14)
            return generateErrorResponse("ERROR: Input seems strange - did you confound latitude and longitude?");

        URL url;
        try {
            switch (endpoint) {
                case "overview":
                    url = builder.generateOverviewUrl(lat, lng);
                    break;
                case "route":
                    url = builder.generateRouteUrl(lat, lng);
                    break;
                default:
                    throw new IllegalArgumentException(endpoint + " not supported!");
            }
        }
        catch (Exception e) {
            return generateErrorResponse("ERROR: Exception generating URL: " + e.getMessage());
        }

        byte[] image;
        try(InputStream in = url.openStream()) {
            image = StreamUtils.copyToByteArray(in);
        }
        catch (Exception e) {
            return generateErrorResponse("ERROR: Exception downloading image: " + e.getMessage());
        }

        try {
            if (store) {
                FileHelper.writeToFile(image, outputFile);
            }
        }
        catch (Exception e) {
            return generateErrorResponse("ERROR: Exception storing image: " + e.getMessage());
        }

        return ResponseEntity
                .ok()
                .contentType(FileHelper.getMediaType(configuration.getOutputFormat()))
                .body(image);
    }

    private ResponseEntity<Object> generateErrorResponse(final String message)
    {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.TEXT_PLAIN)
                .body(message);
    }

}
