package visnode.ws;

/**
 * Http response
 */
public class HttpResponse {

    /** Status code */
    private final int statusCode;
    /** Body */
    private final String body;

    public HttpResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    /**
     * Returns the status code
     *
     * @return int
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the body
     *
     * @return String
     */
    public String getBody() {
        return body;
    }

}
