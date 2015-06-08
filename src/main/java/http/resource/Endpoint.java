package http.resource;

import http.request.HTTPAction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Endpoint {
    String path();
    HTTPAction action();
}
