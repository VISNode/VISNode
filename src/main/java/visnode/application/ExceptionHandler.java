package visnode.application;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import visnode.commons.swing.WindowFactory;
import visnode.commons.swing.components.ExceptionPanel;

/**
 * Exception handler
 */
public class ExceptionHandler {

    /** Singleton instance */
    private static ExceptionHandler instance;
    /** Exception dialog */
    private final JDialog exceptionDialog;
    /** Exception dialog */
    private ExceptionPanel exceptionPanel;
    
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
     * Exception handler
     */
    private ExceptionHandler() {
        exceptionDialog = WindowFactory.modal().title("Error").create((container) -> {
            exceptionPanel = new ExceptionPanel();
            container.add(exceptionPanel);
        });
    }
    
    /**
     * Handles the exception
     * 
     * @param e 
     */
    public void handle(Exception e) {
        SwingUtilities.invokeLater(() -> {
            if (exceptionDialog.isVisible()) {
                exceptionPanel.add(e);
            } else {
                exceptionPanel.set(e);
                exceptionDialog.setVisible(true);
            }
            exceptionDialog.pack();
            exceptionDialog.setLocationRelativeTo(null);
        });
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
