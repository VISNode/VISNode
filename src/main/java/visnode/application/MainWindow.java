package visnode.application;

import javax.swing.JFrame;


/**
 * Application main window
 */
public class MainWindow extends JFrame {

    /**
     * Creates the main window
     */
    public MainWindow() {
        super();
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setSize(1024, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
}
