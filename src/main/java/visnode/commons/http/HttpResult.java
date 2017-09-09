package visnode.commons.http;

import java.nio.charset.Charset;

/**
 * Result of a Http request
 */
public class HttpResult {

    /** Body */
    private final byte[] body;
    
    /**
     * Creates a new Http result
     * @param body 
     */
    protected HttpResult(byte[] body) {
        this.body = body;
    }
    
    /**
     * Returns the body as a String
     * 
     * @return String
     */
    public String asString() {
        return new String(body, Charset.defaultCharset());
    }
    
}
