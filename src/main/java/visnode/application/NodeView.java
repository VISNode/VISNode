package visnode.application;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.gui.JNode;
import visnode.gui.JNodeConnector;

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
        for (String inputParameter : node.getInputParameters()) {
            add(new NodeConnectorView(inputParameter, ConnectionType.INPUT));
        }
        for (String inputParameter : node.getOutputParameters()) {
            add(new NodeConnectorView(inputParameter, ConnectionType.OUTPUT));
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
