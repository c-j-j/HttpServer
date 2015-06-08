package http.builders;

import http.HTTPAction;
import http.auth.AuthenticationHeader;

public class HTTPRequestMessageBuilder {

    public static final String RANGE_TEMPLATE = "Range: %s";
    private static final String CONTENT_LENGTH_TEMPLATE = "Content-Length: %d";
    private HTTPAction action = HTTPAction.GET;
    public static final String AUTHORIZATION_TEMPLATE = "Authorization: %s %s";
    public static final String IF_MATCH_TEMPLATE = "If-Match: %s";
    private String path = "/";
    private AuthenticationHeader authenticationHeader;
    private String body;
    private String ifMatchValue;
    private String rangeText;

    public HTTPRequestMessageBuilder withAction(HTTPAction action) {
        this.action = action;
        return this;
    }

    public HTTPRequestMessageBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public HTTPRequestMessageBuilder withAuthentication(AuthenticationHeader authenticationHeader) {
        this.authenticationHeader = authenticationHeader;
        return this;
    }

    public HTTPRequestMessageBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public HTTPRequestMessageBuilder withIfMatch(String ifMatchTag) {
        this.ifMatchValue = ifMatchTag;
        return this;
    }

    public HTTPRequestMessageBuilder withRange(String rangeText) {
        this.rangeText = rangeText;
        return this;
    }

    public String build() {
        return writeHeader() + body;
    }

    private String writeHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstLine());
        stringBuilder.append(authenticationLine());
        stringBuilder.append(ifMatchLine());
        stringBuilder.append(rangeLine());
        stringBuilder.append(contentLength());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private String authenticationLine() {
        return authenticationHeader != null ? line(String.format(AUTHORIZATION_TEMPLATE,
                "Basic", authenticationHeader.getAuthValue())) : "";
    }

    private String firstLine() {
        return line(String.format("%s %s HTTP/1.1", action, path));
    }

    private String ifMatchLine() {
        return ifMatchValue != null ? line(String.format(IF_MATCH_TEMPLATE, ifMatchValue)) : "";
    }

    private String contentLength() {
        return line(String.format(CONTENT_LENGTH_TEMPLATE, lengthOfBody()));
    }

    private String rangeLine() {
        return rangeText != null ? line(String.format(RANGE_TEMPLATE, rangeText)) : "";
    }

    private int lengthOfBody() {
        if (body == null) {
            return 0;
        } else {
            return body.getBytes().length;
        }
    }

    private String line(String string) {
        return string + "\n";
    }

}
