package http.resource;

import builders.ResponseBuilder;
import http.HTTPAction;
import http.HTTPStatusCode;
import http.Request;
import http.Response;

public class RedirectResource implements Resource {

    @Endpoint(action = HTTPAction.GET, path = "/redirect")
    public Response redirect(Request request){
       return new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).withContent("Redirected").build();
    }
}
