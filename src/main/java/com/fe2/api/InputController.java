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
    public String test(HttpServletResponse response) {
        return overview(49.62499039912012, 10.538893820050225);
    }

    @GetMapping("/overview")
    public String overview(@RequestParam(value = "lat") double lat, @RequestParam(value = "lng") double lng) {

        // Germany: Latitude from 47.40724 to 54.9079 and longitude from 5.98815 to 14.98853.
        if (lat < lng || lat < 47 || lat > 54 || lng < 5 || lng > 14)
            return "ERROR: Input seems strange - did you confound latitude and longitude?";

        URL url;
        try {
            url = builder.generateOverviewUrl(lat, lng);
        }
        catch (Exception e) {
            return "ERROR: Exception generating URL: " + e.getMessage();
        }

        try {
            downloader.downloadImage(url, "overview");
        }
        catch (Exception e) {
            return "ERROR: Exception downloading image: " + e.getMessage();
        }

        return "SUCCESS";
    }
}
