package http.request;

import http.response.HTTPStatusCode;
import http.response.Response;
import http.response.builders.ResponseBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class RequestProcessor implements Consumer<Socket> {

    private Function<Request, Response> requestHandler;
    private BiConsumer<Socket, Response> socketWriter;
    private final Function<Socket, Request> requestParser;

    public RequestProcessor(Function<Socket, Request> requestParser, Function<Request, Response> requestHandler, BiConsumer<Socket, Response> socketWriter) {
        this.requestHandler = requestHandler;
        this.socketWriter = socketWriter;
        this.requestParser = requestParser;
    }

    @Override
    public void accept(Socket socket) {
        try {
            Request request = requestParser.apply(socket);
            socketWriter.accept(socket, requestHandler.apply(request));
        } catch (RuntimeException e) {
            socketWriter.accept(socket, new ResponseBuilder().withStatusCode(HTTPStatusCode.INTERNAL_SERVER_ERROR).build());
        } finally {
            closeSocket(socket);
        }
    }

    private void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
