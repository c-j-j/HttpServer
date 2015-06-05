package http;

import builders.RequestBuilder;
import http.auth.AuthenticationHeader;
import http.auth.AuthenticationType;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;
import java.util.function.Function;

public class RequestDeserialiser implements Function<String, Request> {


    @Override
    public Request apply(String requestPayload) {

        return new RequestBuilder()
                .withHTTPAction(getAction(requestPayload))
                .withPath(getPath(requestPayload))
                .withAuthenticationHeader(Optional.ofNullable(getAuthenticationHeader(findValue(requestPayload, "Authentication"))))
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

    private AuthenticationHeader getAuthenticationHeader(String value) {
        if (StringUtils.isNotEmpty(value)) {
            String[] tokens = value.split(" ");
            return new AuthenticationHeader(AuthenticationType.valueOf(tokens[0]), tokens[1]);
        }
        return null;
    }

    private String getPath(String requestPayload) {
        return requestPayload.split(" ")[1];
    }

    private HTTPAction getAction(String requestPayload) {
        return HTTPAction.valueOf(requestPayload.split(" ")[0]);
    }
}


