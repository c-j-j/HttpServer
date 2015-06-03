package http;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class RequestConsumer implements Consumer<Socket> {

    private final Function<Socket, Request> requestParser;
    private Function<Request, Response> responseGenerator;
    private BiConsumer<Socket, Response> socketWriter;

    public RequestConsumer(Function<Socket, Request> requestParser, Function<Request, Response> responseGenerator, BiConsumer<Socket, Response> socketWriter) {
        this.requestParser = requestParser;
        this.responseGenerator = responseGenerator;
        this.socketWriter = socketWriter;
    }

    @Override
    public void accept(Socket socket) {
        try {
            Request request = requestParser.apply(socket);
            Response response = responseGenerator.apply(request);
            socketWriter.accept(socket, response);
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
