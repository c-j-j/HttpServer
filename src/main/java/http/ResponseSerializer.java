package http;

import org.apache.commons.lang.StringUtils;

public class ResponseSerializer implements Serializer {

    @Override
    public String toPayload(Response response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(headerLine(response));
        stringBuilder.append(location(response));
        stringBuilder.append(content(response));
        return stringBuilder.toString();
    }

    private String location(Response response) {
        return StringUtils.isNotEmpty(response.getLocation()) ?
                String.format("Location: %s\n", response.getLocation()) : "";
    }

    private String content(Response response) {
        return String.format("\n%s", response.getContents());
    }

    private String headerLine(Response response) {
        HTTPStatusCode statusCode = response.getStatusCode();
        return String.format("HTTP/1.1 %d %s\n", statusCode.getCode(), statusCode.getStatus());
    }
}
