import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class HttpServer {
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                System.out.println("Waiting for connections on port 5000");
                new RequestConsumer(socket -> null, r -> null, new SocketWriter()).accept(serverSocket.accept());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
