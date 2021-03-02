package com.fe2.api;

import com.fe2.configuration.Configuration;
import com.fe2.service.ImageDownloader;
import com.fe2.service.UrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class InputController {

    @Autowired
    public UrlBuilder builder;

    @Autowired
    public ImageDownloader downloader;

    @Autowired
    public Configuration configuration;

    @GetMapping("/test")
    public String test(HttpServletResponse response) {
        return overview("49.62499039912012", "10.538893820050225");
    }

    @GetMapping("/overview")
    public String overview(@RequestParam(value = "lat") String lat, @RequestParam(value = "lng") String lng) {

        String outputFileName = configuration.getOverviewOutputFile();

        if (outputFileName == null || outputFileName.isBlank()) {
            return "ERROR: Overview API is not configured!";
        }

        // Simple parameter test if it is a valid double and if location is in Germany.
        // Germany: Latitude from 47.40724 to 54.9079 and longitude from 5.98815 to 14.98853.
        try {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lng);

            if (latitude < 47 || latitude > 54 || longitude < 5 || longitude > 14)
                throw new IllegalArgumentException("Check parameters: Wrong order?");
        }
        catch (Exception e) {
            return "ERROR: Invalid input: " + e.getMessage();
        }

        URL url;
        try {
            url = builder.generateOverviewUrl(lat, lng);
            downloader.downloadImage(url, configuration.getOverviewOutputFile());
        }
        catch (Exception e) {
            return "ERROR: Exception generating URL: " + e.getMessage();
        }

        try {
            downloader.downloadImage(url, outputFileName);
        }
        catch (Exception e) {
            return "ERROR: Exception downloading image: " + e.getMessage();
        }

        try {
            if(!Files.exists(Paths.get(outputFileName))) {
                throw new IOException("Downloaded image cannot be found");
            }
        }
        catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }

        return "SUCCESS";
    }
}
