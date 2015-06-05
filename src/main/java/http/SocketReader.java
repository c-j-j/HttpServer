package http;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.Socket;

public class SocketReader {
    public String getPayload(Socket socket) {
        try {
            return readFromSocket(socket);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String readFromSocket(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuilder payloadBuilder = new StringBuilder();
        readUntilLineBreak(payloadBuilder, bufferedReader);
        return payloadBuilder.toString();
    }

    private void readUntilLineBreak(StringBuilder stringBuilder, BufferedReader bufferedReader) throws IOException {
        String nextLine;
        while (StringUtils.isNotEmpty(nextLine = bufferedReader.readLine())) {
            stringBuilder.append(nextLine).append("\n");
        }
    }
}
