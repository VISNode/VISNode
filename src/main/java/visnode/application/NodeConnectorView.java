
package visnode.application;

import javax.swing.JLabel;
import visnode.gui.JNodeConnector;

/**
 *
 * @author Nícolas Pohren
 */
public class NodeConnectorView extends JNodeConnector {

    /** Attribute */
    private final String attribute;
    /** Connection type */
    private final ConnectionType type;
    
    /**
     * Creates a new connector view
     * 
     * @param attribute
     * @param type 
     */
    public NodeConnectorView(String attribute, ConnectionType type) {
        super(new JLabel(attribute), type == ConnectionType.INPUT ? Configuration.LEFT : Configuration.RIGHT);
        this.attribute = attribute;
        this.type = type;
    }

    /**
     * Returns the attribute
     * 
     * @return String
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Returns the type
     * 
     * @return ConnectionType
     */
    public ConnectionType getType() {
        return type;
    }
    
}
