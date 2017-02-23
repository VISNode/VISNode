package visnode.application;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import visnode.application.mvc.EventListener;
import visnode.application.mvc.PropertyEvent;


/**
 * Application main window
 */
public class MainWindow extends JFrame {

    /** Model */
    private final VISNodeModel model;
    /** Network editor */
    private NetworkEditor networkEditor;
    
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
        setJMenuBar(VISNode.get().getActions().buildMenuBar());
    }
    
    /**
     * Builds the network editor component
     * 
     * @return JComponent
     */
    private JComponent buildNetworkEditor() {
        networkEditor = new NetworkEditor(model.getNetwork());
        model.addEventListener((PropertyEvent event) -> {
            if (event.getPropertyName().equals("network")) {
                networkEditor.setModel(model.getNetwork());
            }
        });
        return networkEditor;
    }
    
}
