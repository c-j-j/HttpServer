import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Function;

public class RequestParser implements Function<Socket, Request> {

    @Override
    public Request apply(Socket socket) {
        String requestPayload = "";
        try {
            requestPayload = IOUtils.toString(socket.getInputStream());
        } catch (IOException ignored) {
        }
        return new Request(HTTPAction.valueOf(getAction(requestPayload)), getPath(requestPayload));
    }

    private String getPath(String requestPayload) {
        return requestPayload.split(" ")[1];
    }

    private String getAction(String requestPayload) {
        return requestPayload.split(" ")[0];
    }
}
