package http.request;

import http.ByteRange;

import java.util.Optional;

public class ByteRangeBuilder {

    private Optional<Integer> from = Optional.empty();
    private Optional<Integer> to = Optional.empty();

    public ByteRangeBuilder withFromRange(Integer from) {
        this.from = Optional.of(from);
        return this;
    }

    public ByteRangeBuilder withToRange(Integer to) {
        this.to = Optional.of(to);
        return this;
    }

    public ByteRange build() {
        return new ByteRange(from, to);
    }

}
