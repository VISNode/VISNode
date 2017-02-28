package visnode.application;

import javax.swing.JComponent;
import javax.swing.JLabel;
import visnode.executor.Node;
import visnode.executor.NodeParameter;
import visnode.gui.JNodeConnector;

/**
 * Node connector view
 */
public class NodeConnectorView extends JNodeConnector {

    /** Node */
    private final Node node;
    /** Node parameter */
    private final NodeParameter parameter;
    /** Connection type */
    private final ConnectionType type;

    /**
     * Creates a new connector view
     *
     * @param node
     * @param parameter
     * @param type
     */
    public NodeConnectorView(Node node, NodeParameter parameter, ConnectionType type) {
        super(type == ConnectionType.INPUT ? Configuration.LEFT : Configuration.RIGHT);
        this.node = node;
        this.parameter = parameter;
        this.type = type;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setComponent(buildComponent());
    }

    /**
     * Builds the components
     * 
     * @return JComponent
     */
    private JComponent buildComponent() {
        if (parameter.getName().equals("image") && type == ConnectionType.OUTPUT) {
            return new ImageNodeComponent(node);
        }
        return new JLabel(parameter.getName());
    }

    /**
     * Returns the attribute
     *
     * @return String
     */
    public String getAttribute() {
        return parameter.getName();
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
