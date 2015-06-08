package http.request;

import http.response.Response;
import http.request.auth.AuthenticationHeader;
import http.url.UrlDecode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private final HTTPAction httpAction;
    private final String uri;
    private final Optional<AuthenticationHeader> authenticationHeader;
    private final Optional<ByteRange> range;
    private final String requestPayload;
    private final long contentLength;
    private Optional<String> ifMatchValue;

    public RequestHeader(HTTPAction httpAction, String uri, Optional<AuthenticationHeader> authenticationHeader, Optional<ByteRange> range, String requestPayload, long contentLength, Optional<String> ifMatchValue) {
        this.httpAction = httpAction;
        this.uri = uri;
        this.authenticationHeader = authenticationHeader;
        this.range = range;
        this.requestPayload = requestPayload;
        this.contentLength = contentLength;
        this.ifMatchValue = ifMatchValue;
    }

    public HTTPAction getAction() {
        return httpAction;
    }

    public String getPath() {
        return uri.substring(0, endOfPathIndex());
    }

    public Response fileBasedResponse(File baseFolder, Request request) {
        return httpAction.getFileResponseHandler().getResponse(baseFolder, request);
    }

    public Optional<AuthenticationHeader> getAuthenticationHeader() {
        return authenticationHeader;
    }

    public Optional<String> getIfMatchValue() {
        return ifMatchValue;
    }

    public String getPayload() {
        return requestPayload;
    }

    public long getContentLength() {
        return contentLength;
    }

    public Map<String, String> getQueryParameters() {
        Map<String, String> queryParameterMap = new HashMap<>();
        for (String queryParameter : splitQueryParameters()) {
            String[] queryParameterTokens = splitQueryParameter(queryParameter);
            queryParameterMap.put(queryParameterTokens[0], UrlDecode.decode(queryParameterTokens[1]));
        }
        return queryParameterMap;
    }

    private String[] splitQueryParameter(String queryParameter) {
        return queryParameter.split("=");
    }

    private String[] splitQueryParameters() {
        return extractQueryParameterSection().split("&");
    }

    private String extractQueryParameterSection() {
        return uri.substring(endOfPathIndex() + 1);
    }

    private int endOfPathIndex() {
        int endOfPathIndex = uri.indexOf("?");
        return endOfPathIndex > 0 ? endOfPathIndex : uri.length();
    }

    public Optional<ByteRange> getRange() {
        return range;
    }
}
