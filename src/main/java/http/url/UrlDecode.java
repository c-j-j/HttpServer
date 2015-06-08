package http.url;

import java.util.HashMap;
import java.util.Map;

public class UrlDecode {

    public static final Map<String, String> DECODE_MAP = new HashMap<String, String>() {{
        put("%20", " ");
        put("%3C", "<");
        put("%3E", ">");
        put("%3D", "=");
        put("%2C", ",");
        put("%21", "!");
        put("%3B", ";");
        put("%2B", "+");
        put("%2D", "-");
        put("%2A", "*");
        put("%26", "&");
        put("%23", "#");
        put("%24", "$");
        put("%5B", "[");
        put("%5D", "]");
        put("%3A", ":");
        put("%22", "\"");
        put("%3F", "?");
        put("%40", "@");
    }};

    public static String decode(String encodedString) {
        String decodedString = encodedString;
        for (String encodedValue : DECODE_MAP.keySet()) {
            decodedString = decodedString.replace(encodedValue, DECODE_MAP.get(encodedValue));
        }
        return decodedString;
    }
}
