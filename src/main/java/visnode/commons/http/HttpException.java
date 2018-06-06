package visnode.commons.http;

/**
 *
 */
public class HttpException extends RuntimeException {

    private final HttpResult httpResult;

    public HttpException(HttpResult httpResult) {
        this.httpResult = httpResult;
    }

    public HttpResult getHttpResult() {
        return httpResult;
    }

}
