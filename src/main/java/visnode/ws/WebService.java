package visnode.ws;

import com.google.gson.Gson;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Web service integration
 */
public class WebService {

    /** Host name */
    public static final String HOSTNAME = "http://localhost:8180/VISNodeWS/v1/";

    /** The web service instance */
    private static WebService instace;
    /** Gson */
    private final Gson gson;

    private WebService() {
        this.gson = new Gson();
    }

    /**
     * Execute a post request
     *
     * @param entity
     * @param data
     * @throws WebServiceException
     */
    public void post(String entity, Object data) throws WebServiceException {
        try {
            new visnode.commons.http.Http().
                    post(gson.toJson(data), HOSTNAME + entity).get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new WebServiceException(ex);
        }
    }

    /**
     * Execute a get request
     *
     * @param entity
     * @return WebServiceResponse
     * @throws WebServiceException
     */
    public WebServiceResponse get(String entity) throws WebServiceException {
        return get(entity, null);
    }

    /**
     * Execute a get request
     *
     * @param entity
     * @param query
     * @return WebServiceResponse
     * @throws WebServiceException
     */
    public WebServiceResponse get(String entity, WebServiceQuery query) throws WebServiceException {
        StringBuilder url = new StringBuilder(HOSTNAME + entity);
        if (query != null) {
            url.append("?query=").
                    append(URLEncoder.encode(query.toString()));
        }
        try {
            return new WebServiceResponse(new visnode.commons.http.Http().get(url.toString()).get());
        } catch (InterruptedException | ExecutionException ex) {
            throw new WebServiceException(ex);
        }
    }

    /**
     * Execute the login request
     *
     * @param user
     * @param password
     * @throws WebServiceException
     */
    public void login(String user, String password) throws WebServiceException {
        Map data = new HashMap();
        data.put("user", user);
        data.put("password", password);
        try {
            new visnode.commons.http.Http().
                    post(gson.toJson(data), HOSTNAME + "login").get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new WebServiceException(ex);
        }
    }

    /**
     * Returns the web service instance
     *
     * @return WebService
     */
    public static WebService get() {
        if (instace == null) {
            instace = new WebService();
        }
        return instace;
    }

}
