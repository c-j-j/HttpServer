package http;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class RequestConsumer implements Consumer<Socket> {

    private Function<Socket, Response> responseGenerator;
    private BiConsumer<Socket, Response> socketWriter;

    public RequestConsumer(Function<Socket, Response> responseGenerator, BiConsumer<Socket, Response> socketWriter) {
        this.responseGenerator = responseGenerator;
        this.socketWriter = socketWriter;
    }

    @Override
    public void accept(Socket socket) {
        try {
            socketWriter.accept(socket, responseGenerator.apply(socket));
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
