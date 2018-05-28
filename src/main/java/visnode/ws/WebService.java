package visnode.ws;

import com.google.gson.Gson;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
     * @throws HttpException
     */
    public void post(String entity, Object data) throws HttpException {
        Http.create().
                url(HOSTNAME + entity).
                post(gson.toJson(data));
    }

    /**
     * Execute a get request
     *
     * @param entity
     * @return WebServiceResponse
     * @throws HttpException
     */
    public WebServiceResponse get(String entity) throws HttpException {
        return get(entity, null);
    }
    
    /**
     * Execute a get request
     *
     * @param entity
     * @param query
     * @return WebServiceResponse
     * @throws HttpException
     */
    public WebServiceResponse get(String entity, WebServiceQuery query) throws HttpException {
        StringBuilder url = new StringBuilder(HOSTNAME + entity);
        if (query != null) {
            url.append("?query=").
                    append(URLEncoder.encode(query.toString()));
        }
        return new WebServiceResponse(Http.create().
                url(url.toString()).
                get());
    }

    /**
     * Execute the login request
     *
     * @param user
     * @param password
     * @throws HttpException
     */
    public void login(String user, String password) throws HttpException {
        Map data = new HashMap();
        data.put("user", user);
        data.put("password", password);
        Http.create().
                url(HOSTNAME + "login").
                post(gson.toJson(data));
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
