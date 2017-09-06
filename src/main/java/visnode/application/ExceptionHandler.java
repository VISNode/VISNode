package visnode.application;

import javax.swing.JOptionPane;

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
    
    /**
     * Handles the exception
     * 
     * @param e 
     */
    public void handle(InvalidOpenFileException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    
}
