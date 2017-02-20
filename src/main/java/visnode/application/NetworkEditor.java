package visnode.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Map;
import javax.swing.JComponent;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.NodeConnection;
import visnode.executor.ProcessNode;
import visnode.gui.JNodeConnection;
import visnode.gui.JNodeConnector;
import visnode.gui.JNodeContainer;

/**
 * Network editor
 */
public class NetworkEditor extends JComponent {

    /** Model */
    private final NodeNetwork model;
    /** Node container */
    private JNodeContainer nodeContainer;

    /**
     * Creates a new Network Editor
     * 
     * @param model 
     */
    public NetworkEditor(NodeNetwork model) {
        this.model = model;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(buildNodeContainer());
        fullUpdate();
    }

    /**
     * Builds the node container
     * 
     * @return JComponent
     */
    private JComponent buildNodeContainer() {
        nodeContainer = new JNodeContainer();
        return nodeContainer;
    }

    /**
     * Fully updates the view based on the model
     */
    private void fullUpdate() {
        nodeContainer.removeAll();
        createNodes();
        createConnections();
    }
    
    /**
     * Creates the nodes
     */
    private void createNodes() {
        for (EditNodeDecorator node : model.getNodes()) {
            nodeContainer.add(buildNodeFor(node));
        }
    }
    
    /**
     * Creates all the connections
     */
    private void createConnections() {
        for (EditNodeDecorator node : model.getNodes()) {
            if (node.getDecorated() instanceof ProcessNode) {
                ProcessNode processNode = (ProcessNode) node.getDecorated();
                for (Map.Entry<String, NodeConnection> entry : processNode.getConnector().getConnections().entrySet()) {
                    buildConnection(node, entry.getKey(), entry.getValue().getNode(), entry.getValue().getAttribute());
                }
            }
        }
    }

    /**
     * Creates a JNode for the model Node
     * 
     * @param node
     * @return JComponent
     */
    private JComponent buildNodeFor(EditNodeDecorator node) {
        return new NodeView(node);
    }

    /**
     * Builds a connection
     * 
     * @param node1
     * @param attribute1
     * @param node2
     * @param attribute2
     */    
    private void buildConnection(Node node1, String attribute1, Node node2, String attribute2) {
        JNodeConnector connector1 = getNodeFor(node1).getConnectorFor(attribute1);
        JNodeConnector connector2 = getNodeFor(node2).getConnectorFor(attribute2);
        if (connector1 == null || connector2 == null) {
            return;
        }        
        connector1.getLeftConnector().connectTo(connector2.getRightConnector());
    }
    
    /**
     * Returns the view for a node
     * 
     * @param node
     * @return NodeView
     */
    private NodeView getNodeFor(Node node) {
        for (int i = 0; i < nodeContainer.getComponentCount(); i++) {
            Component component = nodeContainer.getComponent(i);
            if (component instanceof NodeView) {
                NodeView nodeView = (NodeView) component;
                if (nodeView.getModel() == node || nodeView.getModel().getDecorated() == node) {
                    return nodeView;
                }
            }
        }
        return null;
    }

}
