package http;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SocketReaderTest {

    @Test
    public void readsSocketPayload(){
        String socketPayload = "socketPayload";
        String extractedPayload = new SocketReader().getPayload(new FakeSocket(IOUtils.toInputStream(socketPayload)));
        assertThat(extractedPayload).contains(socketPayload);
    }
}