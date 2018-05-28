package visnode.ws;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Http connection
 */
public class Http {

    /** Request url */
    private String url;

    private Http() {
    }

    /**
     * Sets the request url
     *
     * @param url
     * @return
     */
    public Http url(String url) {
        this.url = url;
        return this;
    }

    /**
     * Execute a post request
     *
     * @param data
     * @return HttpResponse
     * @throws HttpException
     */
    public HttpResponse post(Object data) throws HttpException {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            // Send post request
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(String.valueOf(data).getBytes());
                wr.flush();
            }
            int responseCode = con.getResponseCode();
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            return new HttpResponse(responseCode, response.toString());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }
   
    /**
     * Execute a get request
     *
     * @return HttpResponse
     * @throws HttpException
     */
    public HttpResponse get() throws HttpException {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //add reuqest header
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            int responseCode = con.getResponseCode();
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            return new HttpResponse(responseCode, response.toString());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    public static Http create() {
        return new Http();
    }

}
