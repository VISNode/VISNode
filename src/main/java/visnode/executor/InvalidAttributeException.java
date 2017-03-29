
package visnode.executor;

/**
 * Invalid attribute exception
 */
public class InvalidAttributeException extends RuntimeException {

    /**
     * Creates a new exception
     */
    public InvalidAttributeException(String attribute) {
        super("No such attribute: " + attribute);
    }
    
}
