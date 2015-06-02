import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class RequestConsumer implements Consumer<Socket> {

    private final Function<Socket, Request> requestCreator;
    private Function<Request, Response> responseGenerator;
    private BiConsumer<Socket, Response> socketWriter;

    public RequestConsumer(Function<Socket, Request> requestCreator, Function<Request, Response> responseGenerator, BiConsumer<Socket, Response> socketWriter) {

        this.requestCreator = requestCreator;
        this.responseGenerator = responseGenerator;
        this.socketWriter = socketWriter;
    }

    @Override
    public void accept(Socket socket) {
        try {
            Request request = requestCreator.apply(socket);
            Response response = responseGenerator.apply(request);
            socketWriter.accept(socket, response);
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
