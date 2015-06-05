package http;

import java.net.Socket;
import java.util.function.Function;

public class RequestParser implements Function<Socket, Request> {

    @Override
    public Request apply(Socket socket) {
        String requestPayload = new SocketReader().getPayload(socket);
        System.out.println(requestPayload);
        return new Request(getAction(requestPayload), getPath(requestPayload));
    }

    private String getPath(String requestPayload) {
        return requestPayload.split(" ")[1];
    }

    private HTTPAction getAction(String requestPayload) {
        return HTTPAction.valueOf(requestPayload.split(" ")[0]);
    }
}
