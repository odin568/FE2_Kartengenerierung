package com.fe2.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {

    @GetMapping("/generate")
    public String generate(@RequestParam(value = "lat") String lat, @RequestParam(value = "long") String lng) {
        try {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lng);
        }
        catch (Exception e) {
            return "Invalid input: " + e.getMessage();
        }


        return "Lat=" + String.valueOf(lat) + "Long=" + String.valueOf(lng);
    }
}