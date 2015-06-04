package http;

import org.apache.commons.lang.StringUtils;

public class ResponseSerializer implements Serializer {

    @Override
    public String toPayload(Response response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header(response));
        stringBuilder.append(location(response));
        stringBuilder.append(content(response));
        return stringBuilder.toString();
    }

    private String content(Response response) {
        return String.format("\n%s", response.getContentsAsString());
    }

    private String header(Response response) {
        HTTPStatusCode statusCode = response.getStatusCode();
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(statusLine(statusCode));
        headerBuilder.append(location(response));
        return headerBuilder.toString();
    }

    private String statusLine(HTTPStatusCode statusCode) {
        return String.format("HTTP/1.1 %d %s\n", statusCode.getCode(), statusCode.getStatus());
    }

    private String location(Response response) {
        return StringUtils.isNotEmpty(response.getLocation()) ?
                String.format("Location: %s\n", response.getLocation()) : "";
    }
}
