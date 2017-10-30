package visnode.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.paim.commons.Image;
import visnode.application.mvc.ListAddEvent;
import visnode.application.mvc.ListRemoveEvent;
import visnode.application.mvc.Model;
import visnode.executor.EditNodeDecorator;
import visnode.executor.InputNode;
import visnode.executor.OutputNode;

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
        fireEvent(new ListAddEvent("nodes", nodes));
    }
    
    /**
     * Removes a list of nodes from the network
     * 
     * @param list 
     */
    public void remove(List<EditNodeDecorator> list) {
        nodes.removeAll(list);
        for (EditNodeDecorator node : list) {
            fireEvent(new ListRemoveEvent("nodes", nodes, node));
        }
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
     * @param file
     */
    public void setInput(File file) {
        InputNode decorator = (InputNode) nodes.get(getInputIndex()).getDecorated();
        decorator.setFile(file);
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
    
    public Image getInput() {
        InputNode decorator = (InputNode) nodes.get(getInputIndex()).getDecorated();
        return decorator.getImage();
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
    
    /**
     * Finds the index of the output node
     * 
     * @return int
     */
    public int getOutputIndex() {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getDecorated() instanceof OutputNode) {
                return i;
            }
        }
        return -1;
    }
}
