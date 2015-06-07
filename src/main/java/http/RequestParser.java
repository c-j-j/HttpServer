package http;

import http.request.Request;
import http.request.builder.RequestBuilder;

import java.net.Socket;
import java.util.function.Function;

public class RequestParser implements Function<Socket, Request> {

    @Override
    public Request apply(Socket socket) {
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream());
//            RequestHeader = deserialiser.getHeader(reader.readUntilLineBreak(bufferedReader, ()->true));
//            String body = reader.readNumberOfBytes(bufferedReader, requestHeader.getContentLength()));
//            return new RequestHeaderBuilder().withHeader().withBody().build();
            String requestPayload = new SocketReader().getPayload(socket);
            System.out.println(requestPayload);
            return new RequestBuilder().withHeader(new RequestHeaderDeserialiser().apply(requestPayload)).build();
    }
}
