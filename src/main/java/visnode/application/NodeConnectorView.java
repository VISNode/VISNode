package visnode.application;

import javax.swing.JComponent;
import visnode.executor.Node;
import visnode.executor.NodeParameter;
import visnode.gui.JNodeConnector;
import visnode.gui.ParameterComponent;
import visnode.gui.ParameterComponentWrapper;

/**
 * Node connector view
 */
public class NodeConnectorView extends JNodeConnector {

    /** Node */
    private final Node node;
    /** Parameter */
    private final NodeParameter parameter;
    /** Connection type */
    private final ConnectionType type;
    /** Parameter component factory */
    private final ParameterComponentFactory parameterComponentFactory;

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
        this.parameterComponentFactory = new ParameterComponentFactory();
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
        ParameterComponent component = parameterComponentFactory.create(node, parameter, type);
        return new ParameterComponentWrapper(component, node, parameter, type);
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
     * Returns the attribute type
     *
     * @return Class
     */
    public Class getAttributeType() {
        return parameter.getType();
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
