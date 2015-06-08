package http.url;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlDecodeTest {


    @Test
    public void decodesEncodedValues() {
        for (String assertion : UrlDecode.DECODE_MAP.keySet()) {
            assertThat(UrlDecode.decode(assertion)).isEqualTo(UrlDecode.DECODE_MAP.get(assertion));
        }
    }
}