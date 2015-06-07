package http;

import http.auth.AuthenticationHeader;
import http.builders.HTTPRequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderDeserialiserTest {

    private RequestHeaderDeserialiser requestHeaderDeserialiser;

    @Before
    public void setUp(){
        requestHeaderDeserialiser = new RequestHeaderDeserialiser();
    }

    @Test
    public void parsesGETAction() {
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.GET).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.GET);
    }

    @Test
    public void parsesPOSTAction() throws IOException {
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.POST).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.POST);
    }

    @Test
    public void parsesPUTAction() throws IOException {
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.PUT).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.PUT);
    }

    @Test
    public void parsesHEADAction() throws IOException {
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.HEAD).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.HEAD);
    }

    @Test
    public void parsesPATCHAction() {
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.PATCH).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.PATCH);
    }

    @Test
    public void parsesDELETEAction() {
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(new HTTPRequestBuilder().withAction(HTTPAction.DELETE).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.DELETE);
    }

    @Test
    public void parsesAuthentication(){
        AuthenticationHeader authenticationHeader = new AuthenticationHeader("someAuthValue");
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(new HTTPRequestBuilder().withAuthentication(authenticationHeader).build());
        assertThat(requestHeader.getAuthenticationHeader().get().getAuthValue()).isEqualTo(authenticationHeader.getAuthValue());
    }

    @Test
    public void parsesPath() {
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(new HTTPRequestBuilder().withPath("/SomePath").build());
        assertThat(requestHeader.getPath()).isEqualTo("/SomePath");
    }

    @Test
    public void addsPayloadToRequest(){
        String payload = new HTTPRequestBuilder().withPath("/").build();
        RequestHeader requestHeader = requestHeaderDeserialiser.apply(payload);
        assertThat(requestHeader.getPayload()).isEqualTo(payload);
    }
}