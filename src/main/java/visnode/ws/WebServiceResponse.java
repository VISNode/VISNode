package visnode.ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import visnode.commons.http.HttpResult;

/**
 * Web Service response
 */
public class WebServiceResponse {

    /** Http response */
    private final HttpResult httpResult;

    public WebServiceResponse(HttpResult httpResponse) {
        this.httpResult = httpResponse;
    }

    /**
     * Returns the http value
     *
     * @param <T>
     * @param type
     * @return T
     */
    public <T> T get(TypeToken<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(httpResult.asString(), type.getType());
    }

    /**
     * Returns the HTTP result
     *
     * @return HttpResult
     */
    public HttpResult getHttpResult() {
        return httpResult;
    }

}
