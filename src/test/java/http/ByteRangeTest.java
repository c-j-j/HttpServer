package http;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteRangeTest {

    @Test
    public void offSetWhenLowerBoundSet() {
        int from = 1;
        int to = 4;
        int contentSize = 10;
        assertThat(new ByteRange(from, to).offset(contentSize)).isEqualTo(from);
    }

    @Test
    public void offSetWhenLowerBoundNotSet() {
        int toRange = 6;
        int contentSize = 10;
        assertThat(new ByteRange(Optional.empty(), Optional.of(toRange)).offset(contentSize)).isEqualTo(contentSize - toRange);
    }

    @Test
    public void lengthWhenLowerAndUpperBoundSet() {
        int from = 1;
        int to = 4;
        int contentSize = 10;
        assertThat(new ByteRange(from, to).length(contentSize)).isEqualTo(to - from);
    }

    @Test
    public void lengthWhenLowerBoundNotSet() {
        int toRange = 4;
        int contentSize = 10;
        assertThat(new ByteRange(Optional.empty(), Optional.of(toRange)).length(contentSize)).isEqualTo(toRange);
    }

    @Test
    public void lengthWhenUpperBoundNotSet(){
        int from = 2;
        int contentSize = 10;
        assertThat(new ByteRange(Optional.of(from), Optional.empty()).length(contentSize)).isEqualTo(contentSize - from);
    }
}