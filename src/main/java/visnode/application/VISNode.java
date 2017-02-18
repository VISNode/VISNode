package visnode.application;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

/**
 * Main class application
 */
public class VISNode {

    /** Application instance */
    private static VISNode instance;
    /** Application model */
    private final VISNodeModel model;

    /**
     * Creates a new application
     */
    public VISNode() {
        this.model = new VISNodeModel();
    }
    
    /**
     * Returns the application instance
     * 
     * @return VISNode
     */
    public static VISNode get() {
       if (instance == null)  {
           instance = new VISNode();
       }
       return instance;
    }
    
    /**
     * Main method
     * 
     * @param args 
     */
    public static void main(String[] args) {
       VISNode.get().start(args);
    }
    
    /**
     * Starts the application
     * 
     * @param args
     */
    public void start(String[] args) {
        setupLookAndFeel();
        buildAndShowWindow();
    }
    
    /**
     * Sets up the LookAndFeel
     */
    private void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new SubstanceLookAndFeel(new GraphiteSkin()) {});
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
        }
    }

    /**
     * Builds and shows the main window
     */
    private void buildAndShowWindow() {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = buildWindow();
            window.setVisible(true);
        });
    }

    /**
     * Builds the main window
     */
    private MainWindow buildWindow() {
        MainWindow window = new MainWindow(model);
        window.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        return window;
    }

    /**
     * Returns the model
     * 
     * @return VISNodeModel
     */
    public VISNodeModel getModel() {
        return model;
    }
    
}
