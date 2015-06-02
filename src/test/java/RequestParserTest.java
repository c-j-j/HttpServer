import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    @Test
    public void ParsesAction(){
        FakeSocket socket = new FakeSocket(new ByteArrayInputStream("HTTP Response".getBytes()));
        Request request = new RequestParser().apply(socket);
        assertThat(request.getAction()).isEqualTo(HTTPAction.GET);
    }
}