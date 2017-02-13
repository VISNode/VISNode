package visnode.commons.reflection;

/**
 * Reflection exception
 * 
 * @author Jouwee
 */
public class ReflectionException extends Exception {

    /**
     * Creates a new Reflection exception
     * 
     * @param message
     * @param cause
     */
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new Reflection exception
     * 
     * @param cause
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new Reflection exception
     * 
     * @param message
     */
    public ReflectionException(String message) {
        super(message);
    }

    /**
     * Creates a new Reflection exception
     */
    public ReflectionException() {
        super();
    }

}
