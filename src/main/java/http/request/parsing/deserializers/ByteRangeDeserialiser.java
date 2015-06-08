package http.request.parsing.deserializers;

import http.request.ByteRange;
import http.request.builder.ByteRangeBuilder;
import org.apache.commons.lang.StringUtils;

import java.util.function.Function;

public class ByteRangeDeserialiser implements Function<String, ByteRange> {

    @Override
    public ByteRange apply(String rangeText) {
        rangeText = getNumberRange(rangeText);
        String fromRange = getFromRange(rangeText);
        String toRange = getToRange(rangeText);
        return buildByteRange(fromRange, toRange);
    }

    private ByteRange buildByteRange(String fromRange, String toRange) {
        ByteRangeBuilder byteRangeBuilder = new ByteRangeBuilder();
        if (StringUtils.isNotEmpty(fromRange)) {
            byteRangeBuilder.withFromRange(Integer.valueOf(fromRange));
        }

        if (StringUtils.isNotEmpty(toRange)) {
            byteRangeBuilder.withToRange(Integer.valueOf(toRange));
        }

        return byteRangeBuilder.build();
    }

    private String getToRange(String rangeText) {
        return rangeText.substring(rangeText.indexOf("-") + 1);
    }

    private String getFromRange(String rangeText) {
        return rangeText.substring(0, rangeText.indexOf("-"));
    }

    private String getNumberRange(String rangeText) {
        return rangeText.substring(rangeText.indexOf("=") + 1);
    }
}
