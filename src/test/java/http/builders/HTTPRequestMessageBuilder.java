package http.builders;

import http.HTTPAction;
import http.auth.AuthenticationHeader;

public class HTTPRequestMessageBuilder {

    private HTTPAction action = HTTPAction.GET;
    public static final String AUTHORIZATION_TEMPLATE = "Authorization: %s %s";
    public static final String IF_MATCH_TEMPLATE = "If-Match: %s";
    private String path = "/";
    private AuthenticationHeader authenticationHeader;
    private String body;
    private String ifMatchValue;

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

    private String authenticationLine() {
        return line(String.format(AUTHORIZATION_TEMPLATE,
                "Basic", authenticationHeader.getAuthValue()));
    }

    public String build() {
        return writeHeader() + body;
    }

    private String writeHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstLine());

        if (authenticationHeader != null) {
            stringBuilder.append(authenticationLine());
        }

        if(ifMatchValue != null){
            stringBuilder.append(ifMatchLine());
        }
        stringBuilder.append(contentLength());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private String firstLine() {
        return line(String.format("%s %s HTTP/1.1", action, path));
    }
    private String ifMatchLine() {
        return line(String.format(IF_MATCH_TEMPLATE, ifMatchValue));
    }

    private String contentLength() {
        return line(String.format("Content-Length: %d", lengthOfBody()));
    }

    private int lengthOfBody() {
        if(body==null){
            return 0;
        }else{
            return body.getBytes().length;
        }
    }

    private String line(String string) {
        return string + "\n";
    }

}
