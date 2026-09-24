package com.craftplay777.lemehostauto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlNormalizer {

    private static final Pattern PATRON_VIEW_ID = Pattern.compile("[?&]id=(\\d+)");
    private static final Pattern PATRON_SERVER_ID = Pattern.compile("/server/(\\d+)");

    // Función: agrega https:// si falta, saca el ID del servidor de donde sea que esté, y arma siempre /server/ID/free-plan
    public static String normalizar(String url) {
        String limpia = url.trim();

        if (!limpia.startsWith("http://") && !limpia.startsWith("https://")) {
            limpia = "https://" + limpia;
        }

        String id = null;

        Matcher matcherView = PATRON_VIEW_ID.matcher(limpia);
        if (matcherView.find()) {
            id = matcherView.group(1);
        } else {
            Matcher matcherServer = PATRON_SERVER_ID.matcher(limpia);
            if (matcherServer.find()) {
                id = matcherServer.group(1);
            }
        }

        if (id != null) {
            return "https://lemehost.com/server/" + id + "/free-plan";
        }

        return limpia;
    }
}
