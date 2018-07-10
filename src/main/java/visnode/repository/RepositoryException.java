package visnode.repository;

import visnode.ws.WebServiceException;

/**
 * Repository exception
 */
public class RepositoryException extends Exception {

    public RepositoryException(String message, Exception cause) {
        super(message, cause);
    }

    public RepositoryException(WebServiceException ex) {
        super(ex.getMessage(), ex);
    }

}
