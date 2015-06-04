package http;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.function.Function;

public class RequestParser implements Function<Socket, Request> {

    @Override
    public Request apply(Socket socket) {
        String requestPayload = getRequestPayload(socket);
        System.out.println(requestPayload);
        return new Request(getAction(requestPayload), getPath(requestPayload));
    }

    private String getRequestPayload(Socket socket) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String nextLine;
            while (StringUtils.isNotEmpty(nextLine = bufferedReader.readLine())) {
                stringBuilder.append(nextLine).append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String getPath(String requestPayload) {
        return requestPayload.split(" ")[1];
    }

    private HTTPAction getAction(String requestPayload) {
        return HTTPAction.valueOf(requestPayload.split(" ")[0]);
    }
}
