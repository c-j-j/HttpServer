package http;

import http.builders.HTTPRequestBuilder;
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
    public void parsesGETAction() {
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withAction(HTTPAction.GET).build()));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.GET);
    }

    @Test
    public void parsesPOSTAction() throws IOException {
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withAction(HTTPAction.POST).build()));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.POST);
    }

    @Test
    public void parsesPUTAction() throws IOException {
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withAction(HTTPAction.PUT).build()));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.PUT);
    }

    @Test
    public void parsesHEADAction() throws IOException {
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withAction(HTTPAction.HEAD).build()));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.HEAD);
    }

    @Test
    public void parsesPATCHAction(){
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withAction(HTTPAction.PATCH).build()));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.PATCH);
    }

    @Test
    public void parsesDELETEAction(){
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withAction(HTTPAction.DELETE).build()));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.DELETE);
    }

    @Test
    public void parsesPath(){
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withPath("/SomePath").build()));
        assertThat(requestParser.apply(socket).getPath()).isEqualTo("/SomePath");
    }

    private InputStream inputStream(String httpRequest) {
        return IOUtils.toInputStream(httpRequest);
    }

}