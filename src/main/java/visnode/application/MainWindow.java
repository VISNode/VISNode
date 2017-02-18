package visnode.application;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 * Application main window
 */
public class MainWindow extends JFrame {

    /** Model */
    private final VISNodeModel model;
    
    /**
     * Creates the main window
     * 
     * @param model
     */
    public MainWindow(VISNodeModel model) {
        super();
        this.model = model;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setSize(1024, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buildNetworkEditor());
    }
    
    /**
     * Builds the network editor component
     * 
     * @return JComponent
     */
    private JComponent buildNetworkEditor() {
        return new NetworkEditor(model.getNetwork());
    }
    
}
