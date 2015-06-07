package http.response;

import builders.RequestHeaderBuilder;
import http.HTTPAction;
import http.HTTPStatusCode;
import http.RequestHeader;
import http.Response;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class OPTIONResponseResolverTest {

    private OPTIONResponseResolver optionResponseResolver = new OPTIONResponseResolver();
    private RequestHeader requestHeader = new RequestHeaderBuilder().withHTTPAction(HTTPAction.OPTIONS).build();

    @Test
    public void yieldsOKStatusCode(){
        Response response = optionResponseResolver.getResponse(new File(""), requestHeader);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void providesPossibleOptions(){
        Response response = optionResponseResolver.getResponse(new File(""), requestHeader);
        assertThat(response.getAllowedOptions()).contains(HTTPAction.GET, HTTPAction.HEAD, HTTPAction.POST, HTTPAction.OPTIONS, HTTPAction.PUT);
    }

}