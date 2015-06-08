package http.request;

import http.request.builder.RequestBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.function.Function;

public class RequestParser implements Function<Socket, Request> {

    private final RequestReader requestReader;

    public RequestParser(){
        requestReader = new RequestReader();
    }

    @Override
    public Request apply(Socket socket) {
        try {
            BufferedReader bufferedReader = getBufferedReader(socket);
            RequestHeader header = parseHeader(bufferedReader);
            return new RequestBuilder()
                    .withHeader(header)
                    .withBody(parseBody(bufferedReader, header))
                    .build();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private BufferedReader getBufferedReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private String parseBody(BufferedReader bufferedReader, RequestHeader header) {
        return requestReader.readNumberOfBytes(bufferedReader, (int) header.getContentLength());
    }

    private RequestHeader parseHeader(BufferedReader reader) {
        return new RequestHeaderDeserialiser().apply(requestReader.readUntilLineBreak(reader));
    }
}
