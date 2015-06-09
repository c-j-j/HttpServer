package http.request.parsing;

import http.request.HTTPAction;
import http.request.auth.AuthenticationHeader;
import http.builders.HTTPRequestMessageBuilder;
import http.request.RequestHeader;
import http.request.parsing.deserializers.RequestHeaderDeserializer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderDeserializerTest {

    private RequestHeaderDeserializer requestHeaderDeserializer;

    @Before
    public void setUp() {
        requestHeaderDeserializer = new RequestHeaderDeserializer();
    }

    @Test
    public void parsesGETAction() {
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withAction(HTTPAction.GET).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.GET);
    }

    @Test
    public void parsesPOSTAction() throws IOException {
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withAction(HTTPAction.POST).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.POST);
    }

    @Test
    public void parsesPUTAction() throws IOException {
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withAction(HTTPAction.PUT).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.PUT);
    }

    @Test
    public void parsesHEADAction() throws IOException {
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withAction(HTTPAction.HEAD).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.HEAD);
    }

    @Test
    public void parsesPATCHAction() {
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withAction(HTTPAction.PATCH).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.PATCH);
    }

    @Test
    public void parsesDELETEAction() {
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withAction(HTTPAction.DELETE).build());
        assertThat(requestHeader.getAction()).isEqualTo(HTTPAction.DELETE);
    }

    @Test
    public void parsesAuthentication() {
        AuthenticationHeader authenticationHeader = new AuthenticationHeader("someAuthValue");
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withAuthentication(authenticationHeader).build());
        assertThat(requestHeader.getAuthenticationHeader().get().getAuthValue()).isEqualTo(authenticationHeader.getAuthValue());
    }

    @Test
    public void parsesContentLength() {
        String body = "someBody";
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withBody(body).build());
        assertThat(requestHeader.getContentLength()).isEqualTo(body.getBytes().length);
    }

    @Test
    public void parsesPath() {
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withPath("/SomePath").build());
        assertThat(requestHeader.getPath()).isEqualTo("/SomePath");
    }

    @Test
    public void parsesIfMatchField() {
        String ifMatchValue = "ifMatchValue";
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withIfMatch(ifMatchValue).build());
        assertThat(requestHeader.getIfMatchValue().get()).isEqualTo(ifMatchValue);
    }

    @Test
    public void parsesRangeRequest(){
        RequestHeader requestHeader = requestHeaderDeserializer.apply(new HTTPRequestMessageBuilder().withRange("bytes=1-4").build());
        assertThat(requestHeader.getRange().get().lowerBound()).isEqualTo(1);
        assertThat(requestHeader.getRange().get().upperBound()).isEqualTo(4);
    }

    @Test
    public void addsPayloadToRequest() {
        String payload = new HTTPRequestMessageBuilder().withPath("/").build();
        RequestHeader requestHeader = requestHeaderDeserializer.apply(payload);
        assertThat(requestHeader.getPayload()).isEqualTo(payload);
    }
}