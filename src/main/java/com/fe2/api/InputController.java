package com.fe2.api;

import com.fe2.service.ImageDownloader;
import com.fe2.service.UrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;

@RestController
public class InputController {

    @Autowired
    public UrlBuilder builder;

    @Autowired
    public ImageDownloader downloader;

    @GetMapping("/test")
    public String generate(HttpServletResponse response) {
        return generate("49.62499039912012", "10.538893820050225");
    }

    @GetMapping("/generate")
    public String generate(@RequestParam(value = "lat") String lat, @RequestParam(value = "lng") String lng) {

        // Simple parameter test if it is a valid double and if location is in Germany.
        // Germany: Latitude from 47.40724 to 54.9079 and longitude from 5.98815 to 14.98853.
        try {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lng);

            if (latitude < 47 || latitude > 54 || longitude < 5 || longitude > 14)
                throw new IllegalArgumentException("Check parameters: Wrong order?");
        }
        catch (Exception e) {
            return "Invalid input: " + e.getMessage();
        }

        try {
            URL uri = builder.buildUrl(lat, lng);
            downloader.downloadImage(uri);
        }
        catch (Exception e) {
            return "Exception: " + e.getMessage();
        }

        return "Success!";
    }
}
