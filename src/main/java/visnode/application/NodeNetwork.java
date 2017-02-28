package visnode.application;

import java.util.ArrayList;
import java.util.List;
import visnode.application.mvc.Model;
import visnode.executor.EditNodeDecorator;

/**
 * Node network
 */
public class NodeNetwork implements Model {

    /** Nodes */
    private final List<EditNodeDecorator> nodes;

    /**
     * Creates a new node network
     */
    public NodeNetwork() {
        nodes = new ArrayList<>();
    }
    
    /**
     * Adds a node in the network
     * 
     * @param node 
     */
    public void add(EditNodeDecorator node) {
        nodes.add(node);
    }

    /**
     * Returns the nodes of the network
     * 
     * @return {@code List<EditNodeDecorator>}
     */
    public List<EditNodeDecorator> getNodes() {
        return new ArrayList<>(nodes);
    }
    
}
