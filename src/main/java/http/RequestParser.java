package http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.function.Function;

public class RequestParser implements Function<Socket, Request> {

    @Override
    public Request apply(Socket socket) {
        System.out.println("GOT PAYLOAD");
        String requestPayload = getRequestPayload(socket);
        System.out.println("PAYLOAD RECEIVED");
        return new Request(getAction(requestPayload), getPath(requestPayload));
    }

    private String getRequestPayload(Socket socket) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String nextLine;
            while((nextLine = bufferedReader.readLine()) != null){
                if(nextLine.equals("")){
                   break;
                }
                System.out.println("Line => " + nextLine);
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
        System.out.println(requestPayload);
        return HTTPAction.valueOf(requestPayload.split(" ")[0]);
    }
}
