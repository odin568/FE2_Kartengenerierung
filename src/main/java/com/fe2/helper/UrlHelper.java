package com.fe2.helper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlHelper {
    public static String buildProperParameter(String param, String value) {
        return "&" + param + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
