package visnode.gui;

import javax.swing.JComponent;

/**
 * Node container
 */
public class JNodeContainer extends JComponent {

    /**
     * Creates a new NodeContainer
     */
    public JNodeContainer() {
        super();
        initGui();
        new DragSupport(this);
    }

    /**
     * Inititalizes the interface
     */
    private void initGui() {
        setLayout(null);
    }
    
}
