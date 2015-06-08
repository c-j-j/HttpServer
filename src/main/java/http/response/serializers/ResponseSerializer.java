package http.response.serializers;

import com.google.common.base.Joiner;
import com.google.common.io.ByteSource;
import http.response.HTTPStatusCode;
import http.response.Response;
import org.apache.commons.lang.StringUtils;

public class ResponseSerializer implements Serializer {

    @Override
    public ByteSource toPayload(Response response) {
        return ByteSource.concat(toByteSource(header(response)), response.getBody());
    }

    private String header(Response response) {
        HTTPStatusCode statusCode = response.getStatusCode();
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(statusLine(statusCode));
        headerBuilder.append(location(response));
        headerBuilder.append(contentType(response));
        headerBuilder.append(contentLength(response));
        headerBuilder.append(allowedActions(response));
        headerBuilder.append("\n");
        return headerBuilder.toString();
    }

    private String allowedActions(Response response) {
        return response.getAllowedOptions() != null ?
                String.format("Allow: %s\n", Joiner.on(",").join(response.getAllowedOptions())) : "";
    }

    private String contentLength(Response response) {
        return String.format("Content-Length: %d\n", response.getContentLength());
    }

    private String contentType(Response response) {
        return response.getContentType() != null ? String.format("Content-Type: %s\n", response.getContentType().getDescription()) : "";
    }

    private String statusLine(HTTPStatusCode statusCode) {
        return String.format("HTTP/1.1 %d %s\n", statusCode.getCode(), statusCode.getStatus());
    }

    private String location(Response response) {
        return StringUtils.isNotEmpty(response.getLocation()) ?
                String.format("Location: %s\n", response.getLocation()) : "";
    }

    private ByteSource toByteSource(String header) {
        return ByteSource.wrap(header.getBytes());
    }
}
