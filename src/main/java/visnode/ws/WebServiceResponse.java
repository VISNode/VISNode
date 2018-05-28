package visnode.ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Web Service response
 */
public class WebServiceResponse {
    
    /** Http response */
    private final HttpResponse httpResponse;

    public WebServiceResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
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
        return gson.fromJson(httpResponse.getBody(), type.getType());
    }
    
    /**
     * Returns the http response
     * 
     * @return HttpResponse
     */
    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
    
}
