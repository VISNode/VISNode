package visnode.commons.http;

import java.nio.charset.Charset;

/**
 * Result of a Http request
 */
public class HttpResult {

    /** Status code */
    private final int statusCode;
    /** Body */
    private final byte[] body;

    /**
     * Creates a new Http result
     *
     * @param statusCode
     * @param body
     */
    protected HttpResult(int statusCode, byte[] body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    /**
     * Returns the body as a String
     *
     * @return String
     */
    public String asString() {
        return new String(body, Charset.forName("UTF-8"));
    }

    /**
     * Returns the status code
     *
     * @return int
     */
    public int getStatusCode() {
        return statusCode;
    }

}
