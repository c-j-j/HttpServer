package http.url;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlDecodeTest {

    Map<String, String> decodeAssertions = new HashMap<String, String>() {{
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
    }};

    @Test
    public void decodesEncodedValues() {
        for (String assertion : decodeAssertions.keySet()) {
            assertThat(UrlDecode.decode(assertion)).isEqualTo(decodeAssertions.get(assertion));
        }
    }
}