package http.resource.resources;

import http.resource.Endpoint;
import http.resource.Resource;
import http.response.builders.ResponseBuilder;
import http.request.HTTPAction;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.request.Request;

public class RedirectResource implements Resource {

    @Endpoint(action = HTTPAction.GET, path = "/redirect")
    public Response redirect(Request request){
       return new ResponseBuilder()
               .withStatusCode(HTTPStatusCode.FOUND)
               .withLocation("http://localhost:5000/")
               .build();
    }
}
