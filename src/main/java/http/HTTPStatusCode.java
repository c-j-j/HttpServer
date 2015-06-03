package http;

public enum HTTPStatusCode {
    OK(200, "OK");

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
