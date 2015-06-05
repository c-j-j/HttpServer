package http;

import com.google.common.io.ByteSource;

public interface Serializer {
    ByteSource toPayload(Response response);
}
