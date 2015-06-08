package http.response.serializers;

import com.google.common.io.ByteSource;
import http.response.Response;

public interface Serializer {
    ByteSource toPayload(Response response);
}
