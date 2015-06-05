package http;

import http.auth.AuthenticationHeader;
import http.builders.HTTPRequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestDeserialiserTest {

    private RequestDeserialiser requestDeserialiser;

    @Before
    public void setUp(){
        requestDeserialiser = new RequestDeserialiser();
    }

    @Test
    public void parsesGETAction() {
        Request request = requestDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.GET).build());
        assertThat(request.getAction()).isEqualTo(HTTPAction.GET);
    }

    @Test
    public void parsesPOSTAction() throws IOException {
        Request request = requestDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.POST).build());
        assertThat(request.getAction()).isEqualTo(HTTPAction.POST);
    }

    @Test
    public void parsesPUTAction() throws IOException {
        Request request = requestDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.PUT).build());
        assertThat(request.getAction()).isEqualTo(HTTPAction.PUT);
    }

    @Test
    public void parsesHEADAction() throws IOException {
        Request request = requestDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.HEAD).build());
        assertThat(request.getAction()).isEqualTo(HTTPAction.HEAD);
    }

    @Test
    public void parsesPATCHAction() {
        Request request = requestDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.PATCH).build());
        assertThat(request.getAction()).isEqualTo(HTTPAction.PATCH);
    }

    @Test
    public void parsesDELETEAction() {
        Request request = requestDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.DELETE).build());
        assertThat(request.getAction()).isEqualTo(HTTPAction.DELETE);
    }

    @Test
    public void parsesAuthentication(){
        AuthenticationHeader authenticationHeader = new AuthenticationHeader("someAuthValue");
        Request request = requestDeserialiser.apply(new HTTPRequestBuilder().withAuthentication(authenticationHeader).build());
        assertThat(request.getAuthenticationHeader().get().getAuthValue()).isEqualTo(authenticationHeader.getAuthValue());
    }

    @Test
    public void parsesPath() {
        Request request = requestDeserialiser.apply(new HTTPRequestBuilder().withPath("/SomePath").build());
        assertThat(request.getPath()).isEqualTo("/SomePath");
    }

    @Test
    public void addsPayloadToRequest(){
        String payload = new HTTPRequestBuilder().withPath("/").build();
        Request request = requestDeserialiser.apply(payload);
        assertThat(request.getPayload()).isEqualTo(payload);
    }
}