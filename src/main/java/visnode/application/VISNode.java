package visnode.application;

/**
 * Main class application
 */
public class VISNode {

    /** Application instance */
    private static VISNode instance;

    /**
     * Creates a new application
     */
    public VISNode() {
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
        buildAndShowWindow();
    }

    /**
     * Builds and shows the main window
     */
    private MainWindow buildAndShowWindow() {
        MainWindow window = buildWindow();
        window.setVisible(true);
        return window;
    }

    /**
     * Builds the main window
     */
    private MainWindow buildWindow() {
        MainWindow window = new MainWindow();
        window.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        return window;
    }
    
}
