package visnode.application;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import visnode.application.mvc.PropertyEvent;
import visnode.gui.SplitPanel;
import visnode.gui.ViewPanel;


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
        super("VISNode");
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
        getContentPane().add(buildDummyInterface());
        setJMenuBar(VISNode.get().getActions().buildMenuBar());
    }
    
    /**
     * Builds the network editor component
     * 
     * @return JComponent
     */
    private JComponent buildDummyInterface() {
        NetworkEditor networkEditor = new NetworkEditor(model.getNetwork());
        model.addEventListener((PropertyEvent event) -> {
            if (event.getPropertyName().equals("network")) {
                networkEditor.setModel(model.getNetwork());
            }
        });
        
        SplitPanel split = new SplitPanel(SplitPanel.HORIZONTAL_SPLIT);
        split.setLeftComponent(new ViewPanel(networkEditor));
        split.setRightComponent(new ViewPanel(new ProcessBrowser()));
        split.setDividerLocation(1024);
        return split;
    }
    
}
