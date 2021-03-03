package com.fe2.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class Configuration {

    @Value("${gcp.maps.apiKey}")
    private String gcp_maps_apiKey;

    @Value("${gcp.maps.signingKey:}")
    private String gcp_maps_signingKey; // Optional

    @Value("${gcp.directions.apiKey:}")
    private String gcp_directions_apiKey; // Optional

    @Value("${gcp.directions.signingKey:}")
    private String gcp_directions_signingKey; // Optional

    @Value("${output.folder}")
    private String output_folder;

    @Value("${output.format}")
    private String output_format;

    @Value("${wk.token:}")
    private String wk_token; // Optional

    @Value("#{'${wk.customIcons:}'.split(';')}")
    private List<String> wk_customIcons; // Optional

    public String getGcpMapsApiKey() {
        return gcp_maps_apiKey;
    }

    public String getGcpMapsSigningKey() {
        return gcp_maps_signingKey;
    }

    public String getGcpDirectionsApiKey() {
        return gcp_directions_apiKey;
    }

    public String getGcpDirectionsSigningKey() {
        return gcp_directions_signingKey;
    }

    public String getOutputFolder() {
        return output_folder;
    }

    public String getOutputFormat() {
        return output_format;
    }

    public String getWasserkarteInfoToken() {
        return wk_token;
    }

    public Map<Long, String> getWasserkarteInfoCustomIcons() {
        var map = new TreeMap<Long, String>();
        wk_customIcons.forEach(l -> {
            String[] split = l.split("=");
            map.put(Long.parseLong(split[0]), split[1]);
        });
        return map;
    }
}
