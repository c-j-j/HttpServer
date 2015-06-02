import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FakeSocket extends Socket {
    private boolean closed = false;
    private OutputStream outputStreamWritten;

    public FakeSocket(OutputStream outputStreamWritten) {
        this.outputStreamWritten = outputStreamWritten;
    }

    public FakeSocket(){
        this(new ByteArrayOutputStream());
    }

    public void close() {
        closed = true;
    }

    public OutputStream getOutputStream(){
   return outputStreamWritten;
    }

    public boolean hasBeenClosed() {
        return closed;
    }

    public OutputStream getOutputStreamWritten() {
        return outputStreamWritten;
    }
}
