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
                .withPath(getPath(requestPayload))
                .withAuthenticationHeader(getAuthenticationHeader(findValue(requestPayload, "Authorization")))
                .withRequestPayload(requestPayload)
                .build();
    }

    private String findValue(String requestPayload, String headerField) {
        String[] lines = requestPayload.split("\n");
        for (String line : lines) {
            if (line.startsWith(headerField)) {
                int index = line.indexOf(' ') + 1;
                return line.substring(index);
            }
        }
        return null;
    }

    private Optional<AuthenticationHeader> getAuthenticationHeader(String value) {
        if (StringUtils.isNotEmpty(value)) {
            String[] tokens = value.split(" ");
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


