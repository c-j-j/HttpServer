package http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("Writing to socket");
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(serializer.toPayload(response));
            printWriter.flush();
            printWriter.close();
        } catch (IOException ignored) {
        }
    }
}
