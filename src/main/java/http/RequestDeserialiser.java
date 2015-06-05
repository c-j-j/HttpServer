package http;

import java.util.function.Function;

public class RequestDeserialiser implements Function<String, Request> {


    @Override
    public Request apply(String requestPayload) {
        return new Request(getAction(requestPayload), getPath(requestPayload));
    }

    private String getPath(String requestPayload) {
        return requestPayload.split(" ")[1];
    }

    private HTTPAction getAction(String requestPayload) {
        return HTTPAction.valueOf(requestPayload.split(" ")[0]);
    }
}


