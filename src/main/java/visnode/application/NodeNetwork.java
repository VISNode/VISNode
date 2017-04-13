package visnode.application;

import java.util.ArrayList;
import java.util.List;
import visnode.application.mvc.Model;
import visnode.commons.Image;
import visnode.executor.EditNodeDecorator;
import visnode.executor.InputNode;
import visnode.executor.Node;

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
    
    /**
     * Sets the input image
     * 
     * @param image
     */
    public void setInput(Image image) {
        InputNode decorator = (InputNode) nodes.get(getInputIndex()).getDecorated();
        decorator.setImage(image);
    }
    
    /**
     * Finds the index of the input node
     * 
     * @return int
     */
    public int getInputIndex() {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getDecorated() instanceof InputNode) {
                return i;
            }
        }
        return -1;
    }
}
