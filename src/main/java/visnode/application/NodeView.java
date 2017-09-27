package visnode.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.NodeParameter;
import visnode.gui.EventHelper;
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
        super(model.getName());
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
        EventHelper.addMoveListener(this, (ComponentEvent e) -> {
            model.setPosition(getLocation());
        });
        createConnectors();
    }

    /**
     * Updates the size of the node
     */
    private void updateSize() {
        setSize(getLayout().preferredLayoutSize(this));
        revalidate();
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
        boolean first = true;
        for (NodeParameter inputParameter : node.getInputParameters()) {
            setupParameterConnector(node, inputParameter, ConnectionType.INPUT, first);
            first = false;
        }
        for (NodeParameter inputParameter : node.getOutputParameters()) {
            setupParameterConnector(node, inputParameter, ConnectionType.OUTPUT, first);
            first = false;
        }
    }
    
    /**
     * Setups the parameter connector
     * 
     * @param node
     * @param parameter
     * @param type
     * @param first 
     */
    private void setupParameterConnector(Node node, NodeParameter parameter, ConnectionType type, boolean first) {
        if (!first) {
            add(buildSeparator());
        }
        add(buildLabel(parameter.getName()));
        NodeConnectorView view = new NodeConnectorView(node, parameter, type);
        view.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        add(view);
    }
    
    /**
     * Builds a separator for the node parâmetros
     * 
     * @return JComponent
     */
    private JComponent buildSeparator() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setBorder(new EmptyBorder(0, 0, 0, 25));
        separator.setForeground(new Color(0xAAAAAA));
        separator.setBackground(new Color(0xAAAAAA));
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(0, 10, 0, 10));
        container.add(separator);
        container.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        container.setOpaque(false);
        return container;
    }
    
    /**
     * Build the label for the parameter
     * 
     * @param text
     * @return JComponent
     */
    private JComponent buildLabel(String text) {
        JLabel label = new JLabel(text, JLabel.LEFT);
        label.setBorder(new EmptyBorder(0, 15, 0, 0));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        JPanel container = new JPanel(new BorderLayout(5, 0));
        container.add(label, BorderLayout.WEST);
        container.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        container.setOpaque(false);
        return container;
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
