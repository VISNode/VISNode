package visnode.ws;

import com.google.gson.Gson;
import visnode.commons.http.HttpException;

/**
 * WebService exception
 */
public class WebServiceException extends Exception {

    public WebServiceException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        if (getCause().getCause() instanceof HttpException) {
            return new Gson().fromJson(
                    ((HttpException) getCause().getCause()).getHttpResult().asString(),
                    Message.class
            ).message;
        }
        return "Service unavailable";
    }

    private static class Message {

        private String message;

    }

}
