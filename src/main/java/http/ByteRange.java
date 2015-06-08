package http;

public class ByteRange {

    private final int upperBound;
    private final int lowerBound;

    public ByteRange(String lowerBound, String upperBound) {
        this.lowerBound = Integer.valueOf(lowerBound);
        this.upperBound = Integer.valueOf(upperBound);
    }

    public int lowerBound() {
        return lowerBound;
    }

    public int upperBound() {
        return upperBound;
    }
}
