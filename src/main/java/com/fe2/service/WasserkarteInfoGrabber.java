package com.fe2.service;

import com.fe2.configuration.Configuration;
import com.fe2.helper.UrlHelper;
import com.fe2.hydrants.WasserkarteInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class WasserkarteInfoGrabber {

    @Autowired
    private Configuration configuration;

    public boolean isWasserkarteInfoConfigured()
    {
        return configuration.getWasserkarteInfoToken() != null && !configuration.getWasserkarteInfoToken().isBlank();
    }

    public Optional<WasserkarteInfoResponse> getHydrants(String latitude, String longitude, int numItems, int range)
    {
        String url = "https://api.wasserkarte.info/2.0/getSurroundingWaterSources/?source=alamosam";

        url += UrlHelper.buildProperParameter("token", configuration.getWasserkarteInfoToken());
        url += UrlHelper.buildProperParameter("lat", latitude);
        url += UrlHelper.buildProperParameter("lng", longitude);
        url += UrlHelper.buildProperParameter("range", String.valueOf(range));
        url += UrlHelper.buildProperParameter("numItems", String.valueOf(numItems));

        WasserkarteInfoResponse response;
        try {
            RestTemplate template = new RestTemplateBuilder().build();
            response = template.getForObject(url, WasserkarteInfoResponse.class);
            if (response == null)
                throw new IllegalStateException("response is null");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            // Do not crash. Better empty map than no map at all...
            return Optional.empty();
        }

        return Optional.of(response);
    }
}
