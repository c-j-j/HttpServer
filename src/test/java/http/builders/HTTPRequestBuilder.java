package http.builders;

import http.HTTPAction;
import http.auth.AuthenticationHeader;

public class HTTPRequestBuilder {

    private HTTPAction action = HTTPAction.GET;
    private String path = "/";
    private AuthenticationHeader authenticationHeader;
    private String body;

    public HTTPRequestBuilder withAction(HTTPAction action) {
        this.action = action;
        return this;
    }

    public HTTPRequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public HTTPRequestBuilder withAuthentication(AuthenticationHeader authenticationHeader) {
        this.authenticationHeader = authenticationHeader;
        return this;
    }

    public HTTPRequestBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    private String authenticationLine() {
        return String.format("Authorization: %s %s\n",
                "Basic", authenticationHeader.getAuthValue());
    }

    public String build() {
        String header = writeHeader();
        return header + body;
    }

    private String writeHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstLine());

        if (authenticationHeader != null) {
            stringBuilder.append(authenticationLine());
        }

        stringBuilder.append(contentLength());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private String contentLength() {
        return String.format("Content-Length: %d\n", lengthOfBody());
    }

    private int lengthOfBody() {
        if(body==null){
            return 0;
        }else{
            return body.getBytes().length;
        }
    }

    private String firstLine() {
        return String.format("%s %s HTTP/1.1\n", action, path);
    }
}
