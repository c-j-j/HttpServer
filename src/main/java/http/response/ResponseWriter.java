package http.response;

import http.response.serializers.Serializer;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;

public class ResponseWriter implements BiConsumer<Socket, Response>{

    private final Serializer serializer;

    public ResponseWriter(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void accept(Socket socket, Response response) {
        try {
            System.out.println("Writing to socket");
            serializer.toPayload(response).copyTo(socket.getOutputStream());
            socket.getOutputStream().close();
        } catch (IOException ignored) {
        }
    }
}
