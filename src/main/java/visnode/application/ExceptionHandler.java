package visnode.application;

/**
 * Exception handler
 */
public class ExceptionHandler {

    /** Singleton instance */
    private static ExceptionHandler instance;
    
    /**
     * Returns the singleton instance
     * 
     * @return ExceptionHandler
     */
    public static ExceptionHandler get() {
        if (instance == null) {
            instance = new ExceptionHandler();
        }
        return instance;
    }
    
    /**
     * Handles the exception
     * 
     * @param e 
     */
    public void handle(Exception e) {
        e.printStackTrace();
    }
    
}
