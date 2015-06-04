package http;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;

public class SocketWriter implements BiConsumer<Socket, Response>{

    private final Serializer serializer;

    public SocketWriter(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void accept(Socket socket, Response response) {
        try {
            System.out.println("Writing to socket");
            IOUtils.copy(serializer.toPayload(response).openBufferedStream(), socket.getOutputStream());
        } catch (IOException ignored) {
        }
    }
}
