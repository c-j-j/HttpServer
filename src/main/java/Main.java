import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                System.out.println("Listening on port 5000");
                Socket socket = serverSocket.accept();
                System.out.println("Connection established on port 5000");
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                IOUtils.copy(socket.getInputStream(), socket.getOutputStream());
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
