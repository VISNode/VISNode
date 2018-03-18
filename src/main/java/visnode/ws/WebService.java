package visnode.ws;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 * Web service integration
 */
public class WebService {

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
                url("http://localhost:8080/v1/" + entity).
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
                url("http://localhost:8080/v1/login").
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
