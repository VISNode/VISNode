package visnode.commons.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.apache.commons.io.IOUtils;

/**
 * HTTP helper class
 */
public class Http {

    /**
     * Makes a GET request and returns the result
     * 
     * @param url
     * @return HttpResult
     */
    public CompletableFuture<HttpResult> get(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getSynch(new URL(url));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Makes a GET request and returns the result
     * 
     * @param url
     * @return HttpResult
     */
    public CompletableFuture<HttpResult> get(URL url) {
        return CompletableFuture.supplyAsync(() -> {
            return getSynch(url);
        });
    }
    
    /**
     * Makes a GET request synchronously
     * 
     * @param url
     * @return HttpResult
     */
    private HttpResult getSynch(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            HttpResult result = new HttpResult(IOUtils.toByteArray(conn.getInputStream()));
            return result;
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }
    
}
