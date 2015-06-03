package http;

public interface Serializer {
    String toPayload(Response response);
}
