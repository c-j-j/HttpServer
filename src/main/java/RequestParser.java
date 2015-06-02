import java.net.Socket;
import java.util.function.Function;

public class RequestParser implements Function<Socket, Request> {

    @Override
    public Request apply(Socket socket) {
        return new Request();
    }
}
