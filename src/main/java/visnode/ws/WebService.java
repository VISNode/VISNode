package visnode.ws;

import com.google.gson.Gson;
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
        Http.get().
                url(HOSTNAME + entity).
                post(gson.toJson(data));
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
        Http.get().
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
