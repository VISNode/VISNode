package visnode.commons.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.apache.commons.io.IOUtils;

/**
 * HTTP helper class
 */
public class Http {

    /** Headers */
    private final Map<String, String> headers;

    public Http() {
        this.headers = new HashMap<>();
    }

    /**
     * Adds a HTTP header
     *
     * @param key
     * @param value
     * @return Http
     */
    public Http addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Sets headers into a connection
     * 
     * @param conn 
     */
    private void setHeadersConnection(HttpURLConnection conn) {
        headers.forEach((key, value) -> {
            conn.addRequestProperty(key, value);
        });
    }

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
            } catch (MalformedURLException e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Makes a POST request and returns the result
     *
     * @param data
     * @param url
     * @return HttpResult
     */
    public CompletableFuture<HttpResult> post(Object data, String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return postSynch(data, new URL(url));
            } catch (MalformedURLException e) {
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
     * Makes a POST request and returns the result
     *
     * @param data
     * @param url
     * @return HttpResult
     */
    public CompletableFuture<HttpResult> post(Object data, URL url) {
        return CompletableFuture.supplyAsync(() -> {
            return postSynch(data, url);
        });
    }

    /**
     * Makes a GET request synchronously
     *
     * @param url
     * @return HttpResult
     */
    private HttpResult getSynch(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            setHeadersConnection(conn);
            HttpResult result = new HttpResult(
                    conn.getResponseCode(),
                    IOUtils.toByteArray(conn.getInputStream())
            );
            return result;
        } catch (IOException e) {
            if (conn != null) {
                try {
                    throw new HttpException(new HttpResult(
                            conn.getResponseCode(),
                            IOUtils.toByteArray(conn.getErrorStream())
                    ));
                } catch (IOException ex) {
                }
            }
            throw new CompletionException(e);
        }
    }

    /**
     * Makes a POST request synchronously
     *
     * @param url
     * @return HttpResult
     */
    private HttpResult postSynch(Object data, URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            setHeadersConnection(conn);
            conn.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(String.valueOf(data).getBytes());
                wr.flush();
            }
            HttpResult result = new HttpResult(
                    conn.getResponseCode(),
                    IOUtils.toByteArray(conn.getInputStream())
            );
            return result;
        } catch (IOException e) {
            if (conn != null) {
                try {
                    throw new HttpException(new HttpResult(
                            conn.getResponseCode(),
                            IOUtils.toByteArray(conn.getErrorStream())
                    ));
                } catch (IOException ex) {
                }
            }
            throw new CompletionException(e);
        }
    }

}
