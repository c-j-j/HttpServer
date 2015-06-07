package http.resource;

import builders.ResponseBuilder;
import http.HTTPAction;
import http.HTTPStatusCode;
import http.RequestHeader;
import http.Response;

public class RedirectResource implements Resource {

    @Endpoint(action = HTTPAction.GET, path = "/redirect")
    public Response redirect(RequestHeader requestHeader){
       return new ResponseBuilder()
               .withStatusCode(HTTPStatusCode.FOUND)
               .withLocation("http://localhost:5000/")
               .build();
    }
}
