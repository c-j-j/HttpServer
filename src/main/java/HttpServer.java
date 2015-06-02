import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                new RequestConsumer(null, null, null).accept(serverSocket.accept());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
