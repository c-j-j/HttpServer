package http;

import java.util.Optional;

public class ByteRange {

    private final Optional<Integer> upperBound;
    private final Optional<Integer> lowerBound;

    public ByteRange(Optional<Integer> lowerBound, Optional<Integer> upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public ByteRange(int from, int to) {
        this.lowerBound = Optional.of(from);
        this.upperBound = Optional.of(to);
    }

    public int lowerBound() {
        return lowerBound.get();
    }

    public int upperBound() {
        return upperBound.get();
    }

    public int offset(int contentSize) {
        if (hasLowerBound()) {
            return lowerBound.get();
        } else {
            return contentSize - upperBound();
        }
    }

    public int length(int contentSize) {
        if (hasLowerBound()) {
            return calculateLengthWithLowerBound(contentSize);
        } else {
            return upperBound();
        }
    }

    private int calculateLengthWithLowerBound(int contentSize) {
        if (hasUpperBound()) {
            return upperBound() - lowerBound();
        } else {
            return contentSize - lowerBound();
        }
    }

    public boolean hasUpperBound() {
        return upperBound.isPresent();
    }

    public boolean hasLowerBound() {
        return lowerBound.isPresent();
    }

}
