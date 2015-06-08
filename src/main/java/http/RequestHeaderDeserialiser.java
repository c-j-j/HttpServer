package http;

import builders.RequestHeaderBuilder;
import http.auth.AuthenticationHeader;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;
import java.util.function.Function;

public class RequestHeaderDeserialiser implements Function<String, RequestHeader> {


    @Override
    public RequestHeader apply(String requestPayload) {

        return new RequestHeaderBuilder()
                .withHTTPAction(getAction(requestPayload))
                .withURI(getPath(requestPayload))
                .withAuthenticationHeader(getAuthenticationHeader(findValue(requestPayload, "Authorization")))
                .withRange(extractRange(requestPayload))
                .withIfMatchValue(getIfMatch(requestPayload))
                .withContentLength(contentLength(requestPayload))
                .withRequestPayload(requestPayload)
                .build();
    }

    private Optional<ByteRange> extractRange(String requestPayload) {
        Optional<String> rangeText = findValue(requestPayload, "Range");
        if(rangeText.isPresent()){
            String[] numberRangeTokens = splitRangeNumbers(rangeText.get());
            return Optional.of(new ByteRange(numberRangeTokens[0], numberRangeTokens[1]));
        }else{
            return Optional.empty();
        }
    }

    private String[] splitRangeNumbers(String range) {
        return extractNumbersFromRangeText(range).split("-");
    }

    private String extractNumbersFromRangeText(String range) {
        return range.substring(range.indexOf("=") + 1);
    }

    private Optional<String> getIfMatch(String requestPayload) {
        return findValue(requestPayload, "If-Match");
    }

    private long contentLength(String requestPayload) {
        Optional<String> value = findValue(requestPayload, "Content-Length");
        if (value.isPresent()) {
            return Long.valueOf(value.get());
        } else {
            return 0;
        }
    }

    private Optional<String> findValue(String requestPayload, String headerField) {
        String[] lines = requestPayload.split("\n");
        for (String line : lines) {
            if (line.startsWith(headerField)) {
                return Optional.of(extractValueInline(line));
            }
        }
        return Optional.empty();
    }

    private String extractValueInline(String line) {
        return line.substring(line.indexOf(' ') + 1);
    }

    private Optional<AuthenticationHeader> getAuthenticationHeader(Optional<String> headerLine) {
        if (headerLine.isPresent() && StringUtils.isNotEmpty(headerLine.get())) {
            String[] tokens = headerLine.get().split(" ");
            return Optional.of(new AuthenticationHeader(tokens[1]));
        }
        return Optional.empty();
    }

    private String getPath(String requestPayload) {
        return requestPayload.split(" ")[1];
    }

    private HTTPAction getAction(String requestPayload) {
        return HTTPAction.valueOf(requestPayload.split(" ")[0]);
    }
}


