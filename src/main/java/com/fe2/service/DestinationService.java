package com.fe2.service;

import com.fe2.configuration.Configuration;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    private Configuration configuration;

    public Optional<String> getEncodedPolylines(final double destLat, final double destLng)
    {
        if (!configuration.isDirectionsApiEnabled())
            return Optional.empty();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(configuration.getGcpDirectionsApiKey())
                .build();

        DirectionsResult response = new DirectionsApiRequest(context)
                .origin(new LatLng(configuration.getGcpDirectionsOriginLat(), configuration.getGcpDirectionsOriginLng()))
                .destination(new LatLng(destLat, destLng))
                .awaitIgnoreError();

        if (response == null || response.routes == null || response.routes.length == 0 || response.routes[0].overviewPolyline == null) {
            return Optional.empty();
        }

        return Optional.of(response.routes[0].overviewPolyline.getEncodedPath());
    }


}
