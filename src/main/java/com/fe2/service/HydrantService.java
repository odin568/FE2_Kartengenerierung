package com.fe2.service;

import com.fe2.configuration.Configuration;
import com.fe2.helper.UrlHelper;
import com.fe2.hydrants.WasserkarteInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HydrantService {

    @Autowired
    private Configuration configuration;

    /**
     * Get Hydrants from Wasserkarte.Info
     * @param latitude
     * @param longitude
     * @param numItems
     * @param range
     * @return Optional response. Might be empty if no token present or on any other error.
     */
    public Optional<WasserkarteInfoResponse> getHydrants(double latitude, double longitude, int numItems, int range)
    {
        // Check if configured at all
        if (configuration.getWasserkarteInfoToken() == null || configuration.getWasserkarteInfoToken().isBlank())
            return Optional.empty();

        String url = "https://api.wasserkarte.info/2.0/getSurroundingWaterSources/?source=alamosam";

        url += UrlHelper.buildProperParameter("token", configuration.getWasserkarteInfoToken());
        url += UrlHelper.buildProperParameter("lat", String.valueOf(latitude));
        url += UrlHelper.buildProperParameter("lng", String.valueOf(longitude));
        url += UrlHelper.buildProperParameter("range", String.valueOf(range));
        url += UrlHelper.buildProperParameter("numItems", String.valueOf(numItems));

        WasserkarteInfoResponse response;
        try {
            RestTemplate template = new RestTemplateBuilder().build();
            response = template.getForObject(url, WasserkarteInfoResponse.class);
            if (response == null)
                throw new IllegalStateException("Response is null");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            // Do not crash. Better empty map than no map at all...
            return Optional.empty();
        }

        return Optional.of(response);
    }

    /**
     * Get configured custom Icons. In addition checks if they are reachable.
     * @return Map of custom Icons per SourceTypeId.
     */
    public Map<Long, String> getCustomIcons() {

        var configuredIcons = configuration.getWasserkarteInfoCustomIcons();
        List<Long> invalidUrls = new ArrayList<>();

        for (Map.Entry<Long, String> entry : configuredIcons.entrySet()) {
            HttpURLConnection huc = null;
            try {
                URL u = new URL ( entry.getValue());

                huc = (HttpURLConnection)u.openConnection();
                huc.setRequestMethod ("GET");
                huc.connect();
                if (huc.getResponseCode() != 200) {
                    throw new IOException("Returned " + huc.getResponseCode());
                }

            }
            catch (Exception e) {
                System.out.println("Configured Icon [" + entry.getKey() + "] does not exist: " + e.getMessage());
                invalidUrls.add(entry.getKey());
            }
            finally {
                if (huc != null)
                    huc.disconnect();
            }
        }

        invalidUrls.forEach(configuredIcons::remove);

        return configuredIcons;
    }
}
