package http;

import builders.ResponseBuilder;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class RequestConsumer implements Consumer<Socket> {

    private Function<RequestHeader, Response> responseGenerator;
    private BiConsumer<Socket, Response> socketWriter;
    private final Function<Socket, RequestHeader> requestParser;

    public RequestConsumer(Function<RequestHeader, Response> responseGenerator, BiConsumer<Socket, Response> socketWriter, Function<Socket, RequestHeader> requestParser) {
        this.responseGenerator = responseGenerator;
        this.socketWriter = socketWriter;
        this.requestParser = requestParser;
    }

    @Override
    public void accept(Socket socket) {
        try {
            RequestHeader requestHeader = requestParser.apply(socket);
            socketWriter.accept(socket, responseGenerator.apply(requestHeader));
        } catch (RuntimeException e) {
            socketWriter.accept(socket, new ResponseBuilder().withStatusCode(HTTPStatusCode.INTERNAL_SERVER_ERROR).build());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
