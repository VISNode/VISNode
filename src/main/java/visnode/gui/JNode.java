package visnode.gui;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

/**
 * Node
 */
public class JNode extends JComponent {

    /** Title */
    private final String title;
    
    /**
     * Node
     */
    public JNode() {
        this("");
    }
    
    /**
     * Node
     * 
     * @param title Title
     */
    public JNode(String title) {
        super();
        this.title = title;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setBorder(new JNodeBorder(this, title));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    /**
     * Returns the connectors for this node
     * 
     * @return Set<JNodeConnector>
     */
    public Set<JNodeConnector> getConnectors() {
        Set<JNodeConnector> connectors = new HashSet<>();
        for (int i = 0; i < getComponentCount(); i++) {
            Component child = getComponent(i);
            if (child instanceof JNodeConnector) {
                connectors.add((JNodeConnector) child);
            }
        }
        return connectors;
    }
    /**
     * Returns the connections for this node
     * 
     * @return Set<JNodeConnection>
     */
    public Set<JNodeConnection> getConnections() {
        return getParentNodeContainer().getConnections(this);
    }

    /**
     * Returns the parent node container
     * 
     * @return JNodeContainer
     */
    public JNodeContainer getParentNodeContainer() {
        if (getParent() instanceof JNodeContainer) {
            return (JNodeContainer) getParent();
        } else {
            throw new IllegalStateException("JNode must be inside a JNodeContainer!");
        }
    }

}
