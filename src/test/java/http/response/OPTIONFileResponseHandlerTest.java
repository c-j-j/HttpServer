package http.response;

import http.request.builder.RequestHeaderBuilder;
import http.HTTPAction;
import http.HTTPStatusCode;
import http.Response;
import http.request.Request;
import http.request.builder.RequestBuilder;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionFileResponseHandlerTest {

    private OptionFileResponseHandler optionResponseResolver = new OptionFileResponseHandler();
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