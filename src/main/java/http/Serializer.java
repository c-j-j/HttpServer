package http;

import com.google.common.io.CharSource;

public interface Serializer {
    CharSource toPayload(Response response);
}
