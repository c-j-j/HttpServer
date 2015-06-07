package http;

public enum HTTPStatusCode {
    OK(200, "OK"),
    FOUND(302, "Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_FOUND(404, "Not Found"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    NO_CONTENT(204, "No Content"),
    UNAUTHORIZED(401, "Unauthorized"), ;

    private int code;
    private String status;

    HTTPStatusCode(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
