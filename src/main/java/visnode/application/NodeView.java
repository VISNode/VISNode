package visnode.application;

import java.awt.Component;
import java.awt.event.HierarchyEvent;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.NodeParameter;
import visnode.gui.JNode;

/**
 * View for the node
 */
public class NodeView extends JNode {

    /** Model */
    private final EditNodeDecorator model;

    /**
     * Creates the view
     * 
     * @param model 
     */
    public NodeView(EditNodeDecorator model) {
        super();
        this.model = model;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setBounds(model.getPosition().x, model.getPosition().y, 50, 50);
        addHierarchyListener((HierarchyEvent e) -> {
            updateSize();
        });
        createConnectors();
    }

    /**
     * Updates the size of the node
     */
    private void updateSize() {
        setSize(getLayout().preferredLayoutSize(NodeView.this));
    }

    /**
     * Creates the connector
     */
    private void createConnectors() {
        createConnectors(model);
    }

    /**
     * Creates the connectors 
     * 
     * @param node 
     */
    private void createConnectors(Node node) {
        for (NodeParameter inputParameter : node.getInputParameters()) {
            add(new NodeConnectorView(node, inputParameter, ConnectionType.INPUT));
        }
        for (NodeParameter inputParameter : node.getOutputParameters()) {
            add(new NodeConnectorView(node, inputParameter, ConnectionType.OUTPUT));
        }
    }

    /**
     * Returns the model
     * 
     * @return EditNodeDecorator
     */
    public EditNodeDecorator getModel() {
        return model;
    }

    /**
     * Returns a connector for the attribute
     * 
     * @param attribute
     * @param type
     * @return NodeConnectorView
     */
    public NodeConnectorView getConnectorFor(String attribute, ConnectionType type) {
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
            if (component instanceof NodeConnectorView) {
                NodeConnectorView connector = (NodeConnectorView) component;
                if (connector.getAttribute().equals(attribute) && connector.getType() == type) {
                    return connector;
                }
            }
        }
        return null;
    }
    
}
