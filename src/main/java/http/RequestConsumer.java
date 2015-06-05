package http;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class RequestConsumer implements Consumer<Socket> {

    private Function<Request, Response> responseGenerator;
    private BiConsumer<Socket, Response> socketWriter;
    private final Function<Socket, Request> requestParser;

    public RequestConsumer(Function<Request, Response> responseGenerator, BiConsumer<Socket, Response> socketWriter, Function<Socket, Request> requestParser) {
        this.responseGenerator = responseGenerator;
        this.socketWriter = socketWriter;
        this.requestParser = requestParser;
    }

    @Override
    public void accept(Socket socket) {
        try {
            Request request = requestParser.apply(socket);
            socketWriter.accept(socket, responseGenerator.apply(request));
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
