package com.fe2.api;

import com.fe2.configuration.Configuration;
import com.fe2.helper.FileHelper;
import com.fe2.service.ImageDownloader;
import com.fe2.service.UrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class InputController {

    @Autowired
    public UrlBuilder builder;

    @Autowired
    public ImageDownloader downloader;

    @Autowired
    public Configuration configuration;

    // For Debug purposes
    @GetMapping("/testOverview")
    public ResponseEntity<Object> testOverview() {
        return overview(49.646412071556114, 10.564397866729674);
    }
    // For Debug purposes
    @GetMapping("/testRoute")
    public ResponseEntity<Object> testRoute() {
        return route(49.646412071556114, 10.564397866729674);
    }

    /*
    Example: http://localhost:8080/overview?lat=49.646412071556114&lng=10.564397866729674
     */
    @GetMapping("/overview")
    public ResponseEntity<Object> overview(@RequestParam(value = "lat") double lat, @RequestParam(value = "lng") double lng)
    {
        Path outputFile = FileHelper.getFullOutputFilePath(configuration.getOutputFolder(), "overview", configuration.getOutputFormat());
        try {
            if (Files.exists(outputFile))
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
            url = builder.generateOverviewUrl(lat, lng);
        }
        catch (Exception e) {
            return generateErrorResponse("ERROR: Exception generating URL: " + e.getMessage());
        }

        try {
            downloader.downloadImage(url, outputFile);
        }
        catch (Exception e) {
            return generateErrorResponse("ERROR: Exception downloading image: " + e.getMessage());
        }

        return generateImageResponse(outputFile);
    }

    /*
    Example: http://localhost:8080/route?lat=49.646412071556114&lng=10.564397866729674
     */
    @GetMapping("/route")
    public ResponseEntity<Object> route(@RequestParam(value = "lat") double lat, @RequestParam(value = "lng") double lng)
    {
        Path outputFile = FileHelper.getFullOutputFilePath(configuration.getOutputFolder(), "route", configuration.getOutputFormat());
        try {
            if (Files.exists(outputFile))
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
            url = builder.generateRouteUrl(lat, lng);
        }
        catch (Exception e) {
            return generateErrorResponse("ERROR: Exception generating URL: " + e.getMessage());
        }

        try {
            downloader.downloadImage(url, outputFile);
        }
        catch (Exception e) {
            return generateErrorResponse("ERROR: Exception downloading image: " + e.getMessage());
        }

        return generateImageResponse(outputFile);
    }

    private ResponseEntity<Object> generateImageResponse(final Path outputFile)
    {
        if (Files.exists(outputFile)) {
            try(InputStream image = Files.newInputStream(outputFile)) {
                return ResponseEntity
                        .ok()
                        .contentType(FileHelper.getMediaType(configuration.getOutputFormat()))
                        .body(StreamUtils.copyToByteArray(image));
            }
            catch (IOException e) {
                return generateErrorResponse("ERROR: Exception opening image: " +  e.getMessage());
            }
        }
        return generateErrorResponse("ERROR: Image not found");
    }

    private ResponseEntity<Object> generateErrorResponse(final String message)
    {
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(message);
    }
}
