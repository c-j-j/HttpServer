package http.resource;

import builders.ResponseBuilder;
import http.HTTPAction;
import http.HTTPStatusCode;
import http.Request;
import http.Response;

public class LogsResource implements Resource{

    @Endpoint(action = HTTPAction.GET, path = "/logs")
    public Response logs(Request request){
       return new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
    }
}
