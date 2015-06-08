package http.request.handlers.file;

import http.request.HTTPAction;
import http.HTTPStatusCode;
import http.response.Response;
import http.request.Request;
import http.request.builder.RequestBuilder;
import http.request.builder.RequestHeaderBuilder;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionFileRequestHandlerTest {

    private OptionFileRequestHandler optionResponseResolver = new OptionFileRequestHandler();
    private Request request = new RequestBuilder().withHeader(new RequestHeaderBuilder().withHTTPAction(HTTPAction.OPTIONS).build()).build();

    @Test
    public void yieldsOKStatusCode(){
        Response response = optionResponseResolver.getResponse(new File(""), request);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void providesPossibleOptions(){
        Response response = optionResponseResolver.getResponse(new File(""), request);
        assertThat(response.getAllowedOptions()).contains(HTTPAction.GET, HTTPAction.HEAD, HTTPAction.POST, HTTPAction.OPTIONS, HTTPAction.PUT);
    }

}