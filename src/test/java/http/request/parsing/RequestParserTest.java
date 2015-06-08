package http.request.parsing;

import http.fakes.FakeSocket;
import http.request.HTTPAction;
import http.builders.HTTPRequestMessageBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    private RequestParser requestParser;

    @Before
    public void setUp() throws Exception {
        requestParser = new RequestParser();
    }

    @Test
    public void readsHeader() {
        Socket socket = new FakeSocket(inputStream(new HTTPRequestMessageBuilder().withAction(HTTPAction.GET).build()));
        assertThat(requestParser.apply(socket).getHeader().getAction()).isEqualTo(HTTPAction.GET);
    }

    @Test
    public void readsBody() throws IOException {
        String body = "requestBody";
        Socket socket = new FakeSocket(inputStream(new HTTPRequestMessageBuilder().withBody(body).build()));
        assertThat(requestParser.apply(socket).getBody()).isEqualTo(body);
    }

    private InputStream inputStream(String httpRequest) {
        return IOUtils.toInputStream(httpRequest);
    }

}