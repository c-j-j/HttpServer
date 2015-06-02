import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.BiConsumer;

public class SocketWriter implements BiConsumer<Socket, Response>{

    @Override
    public void accept(Socket socket, Response response) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("Writing to socket");
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write("HTTP/1.1 200 OK\n\nHello");
            printWriter.flush();
            printWriter.close();
        } catch (IOException ignored) {
        }
    }
}
