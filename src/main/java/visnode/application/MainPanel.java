package visnode.application;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import visnode.application.mvc.PropertyEvent;
import visnode.gui.SplitPanel;
import visnode.gui.ViewPanel;

/**
 * Application main window
 */
public class MainPanel extends JPanel {

    /** Model */
    private final VISNodeModel model;
    /** Network editor */
    private NetworkEditor networkEditor;
    
    /**
     * Creates the main window
     * 
     * @param model
     */
    public MainPanel(VISNodeModel model) {
        super();
        this.model = model;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        JToolBar toolbar = new JToolBar();
        toolbar.add(new ActionNew());
        toolbar.add(new ActionOpen());
        toolbar.add(new ActionSave());
        toolbar.addSeparator();
        toolbar.add(new ActionChallengeRun());
        toolbar.add(new ActionChallengeProblem());
        toolbar.addSeparator();
        toolbar.add(new ActionUser());
        add(toolbar, BorderLayout.NORTH);
        add(buildDummyInterface());
    }    
    
    /**
     * Builds the network editor component
     * 
     * @return JComponent
     */
    private JComponent buildDummyInterface() {
        networkEditor = new NetworkEditor(model.getNetwork());
        model.addEventListener(PropertyEvent.class, (PropertyEvent event) -> {
            if (event.getPropertyName().equals("network")) {
                networkEditor.setModel(model.getNetwork());
            }
        });
        SplitPanel split = new SplitPanel(SplitPanel.HORIZONTAL_SPLIT);
        split.setLeftComponent(new ViewPanel(networkEditor));
        split.setRightComponent(new ViewPanel(new ProcessBrowser()));
        split.setResizeWeight(1);
        return split;
    }

    /**
     * Returns the network editor
     * 
     * @return NetworkEditor
     */
    public NetworkEditor getNetworkEditor() {
        return networkEditor;
    }
    
}
