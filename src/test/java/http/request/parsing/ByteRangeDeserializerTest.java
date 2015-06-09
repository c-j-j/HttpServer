package http.request.parsing;

import http.request.ByteRange;
import http.request.parsing.deserializers.ByteRangeDeserializer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteRangeDeserializerTest {

    @Test
    public void parsesToAndFromRange(){
        ByteRange byteRange = new ByteRangeDeserializer().apply("bytes=1-4");
        assertThat(byteRange.lowerBound()).isEqualTo(1);
        assertThat(byteRange.upperBound()).isEqualTo(4);
    }

    @Test
    public void parsesOnlyFromRange(){
        ByteRange byteRange = new ByteRangeDeserializer().apply("bytes=1-");
        assertThat(byteRange.lowerBound()).isEqualTo(1);
        assertThat(byteRange.hasUpperBound()).isFalse();
    }

    @Test
    public void parsesOnlyToRange(){
        ByteRange byteRange = new ByteRangeDeserializer().apply("bytes=-3");
        assertThat(byteRange.upperBound()).isEqualTo(3);
        assertThat(byteRange.hasLowerBound()).isFalse();
    }
}