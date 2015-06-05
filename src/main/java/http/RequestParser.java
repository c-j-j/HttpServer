package http;

import java.net.Socket;
import java.util.function.Function;

public class RequestParser implements Function<Socket, Request> {

    @Override
    public Request apply(Socket socket) {
        String requestPayload = new SocketReader().getPayload(socket);
        System.out.println(requestPayload);
        return new RequestDeserialiser().apply(requestPayload);
    }
}
