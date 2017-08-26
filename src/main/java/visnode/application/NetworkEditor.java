package visnode.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import visnode.application.mvc.ListAddEvent;
import visnode.application.mvc.ListRemoveEvent;
import visnode.executor.AttacherNode;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.NodeConnection;
import visnode.gui.JConnectorPoint;
import visnode.gui.JNode;
import visnode.gui.JNodeConnector;
import visnode.gui.JNodeContainer;
import visnode.gui.NodeConnectionEvent;
import visnode.gui.NodeConnectionListener;
import visnode.gui.Selection;

/**
 * Network editor
 */
public class NetworkEditor extends JComponent {

    /** Model */
    private NodeNetwork model;
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
        model.addEventListener(ListAddEvent.class, (ListAddEvent evt) -> {
            if (evt.getListName().equals("nodes")) {
                SwingUtilities.invokeLater(() -> {
                    nodeContainer.add(buildNodeFor((EditNodeDecorator) evt.getAddedItem()));
                });
            }
        });
        model.addEventListener(ListRemoveEvent.class, (ListRemoveEvent evt) -> {
            if (evt.getListName().equals("nodes")) {
                SwingUtilities.invokeLater(() -> {
                    nodeContainer.remove(getNodeFor((Node) evt.getRemovedBean()));
                    nodeContainer.repaint();
                });
            }
        });
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setTransferHandler(new ProcessTransferHandler());
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
        nodeContainer.addNodeConnectionListener(new NodeConnectionSynchronizer());
        return nodeContainer;
    }

    /**
     * Fully updates the view based on the model
     */
    private void fullUpdate() {
        SwingUtilities.invokeLater(() -> {
            nodeContainer.removeAll();
            createNodes();
            createConnections();
            repaint();
        });
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
            for (Map.Entry<String, NodeConnection> entry : node.getConnector().getConnections().entrySet()) {
                buildConnection(node, entry.getKey(), entry.getValue().getLeftNode(), entry.getValue().getLeftAttribute());
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
        JNodeConnector connector1 = getNodeFor(node1).getConnectorFor(attribute1, ConnectionType.INPUT);
        JNodeConnector connector2 = getNodeFor(node2).getConnectorFor(attribute2, ConnectionType.OUTPUT);
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

    /**
     * Returns the model
     * 
     * @return NodeNetwork
     */
    public NodeNetwork getModel() {
        return model;
    }

    /**
     * Sets the model
     * 
     * @param model 
     */
    public void setModel(NodeNetwork model) {
        this.model = model;
        fullUpdate();
    }
    
    /**
     * Returns the selection
     * 
     * @return Selection
     */
    public Selection<NodeView> getSelection() {
        Stream<JNode> selection = nodeContainer.getSelection().stream();
        List<NodeView> converted = selection.map((jnode) -> (NodeView) jnode).collect(Collectors.toList());
        return new Selection<>(converted);
    }

    /**
     * Node connection synchronizer
     */
    private static class NodeConnectionSynchronizer implements NodeConnectionListener {

        @Override
        public void connectionCreated(NodeConnectionEvent evt) {
            NodeConnectorView leftConnector = (NodeConnectorView) ((JConnectorPoint)evt.getConnection().getFirst()).getParentNodeConnector();
            NodeView leftView = (NodeView) leftConnector.getParentNode();
            Node leftNode = leftView.getModel();
            String leftAttr = leftConnector.getAttribute();
            //
            NodeConnectorView rightConnector = (NodeConnectorView) ((JConnectorPoint)evt.getConnection().getSecond()).getParentNodeConnector();
            NodeView rightView = (NodeView) rightConnector.getParentNode();
            EditNodeDecorator rightNodeDecorator = (EditNodeDecorator) rightView.getModel();
            AttacherNode rightNode = (AttacherNode) rightNodeDecorator.getDecorated();
            String rightAttr = rightConnector.getAttribute();
            //
            rightNode.addConnection(rightAttr, leftNode, leftAttr);
        }

        @Override
        public void connectionRemoved(NodeConnectionEvent evt) {
            NodeConnectorView rightConnector = (NodeConnectorView) ((JConnectorPoint)evt.getConnection().getSecond()).getParentNodeConnector();
            NodeView rightView = (NodeView) rightConnector.getParentNode();
            EditNodeDecorator rightNodeDecorator = (EditNodeDecorator) rightView.getModel();
            AttacherNode rightNode = (AttacherNode) rightNodeDecorator.getDecorated();
            String rightAttr = rightConnector.getAttribute();
            //
            rightNode.removeConnection(rightAttr);
        }
    }
    
}
