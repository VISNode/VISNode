package visnode.executor;

import java.util.HashMap;
import java.util.Map;

/**
 * Node connector
 */
public class NodeConnector {

    /** Output node */
    private transient final Node output;
    /** Node connections */
    private final Map<String, NodeConnection> connections;

    /**
     * Create a new node connector
     * 
     * @param output
     */
    public NodeConnector(Node output) {
        this.output = output;
        this.connections = new HashMap<>();
    }

    /**
     * Adds connection
     *
     * @param attribute
     * @param node
     * @param attributeNode
     */
    public void addConnection(String attribute, Node node, String attributeNode) {
        connections.put(attribute, new NodeConnection(node, attributeNode, output, attribute));
    }
    
    /**
     * Removes an connection 
     * 
     * @param attribute 
     */
    public void removeConnection(String attribute) {
        connections.get(attribute).disconnect();
        connections.remove(attribute);
    }

    /**
     * Gets connection
     *
     * @param attribute
     * @return NodeConnection
     */
    public NodeConnection getConnection(String attribute) {
        return connections.get(attribute);
    }

    /**
     * Returns the connections
     * 
     * @return {@code Map<String, NodeConnection>}
     */
    public Map<String, NodeConnection> getConnections() {
        return new HashMap<>(connections);
    }

}
