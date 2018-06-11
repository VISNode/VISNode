package visnode.application;

import java.util.ArrayList;
import java.util.List;
import visnode.application.mvc.ListAddEvent;
import visnode.application.mvc.ListRemoveEvent;
import visnode.application.mvc.Model;
import visnode.commons.DynamicValue;
import visnode.executor.EditNodeDecorator;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;
import visnode.pdi.process.ImageInput;
import visnode.pdi.process.InputProcess;

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
            node.dispose();
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
     * Returns the output image
     *
     * @return DynamicValue
     */
    public DynamicValue getOutput() {
        OutputNode decorator = getOutputNode();
        return decorator.getValue();
    }

    /**
     * Returns the output node
     * 
     * @return 
     */
    public OutputNode getOutputNode() {
        return (OutputNode) nodes.get(getOutputIndex()).getDecorated();
    }
    
    /**
     * Sets the input image
     *
     * @param image
     */
    public void setInput(ImageInput image) {
        nodes.get(getInputIndex()).getDecorated().setInput("file", image);
    }

    /**
     * Finds the index of the input node
     *
     * @return int
     */
    public int getInputIndex() {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getDecorated() instanceof ProcessNode && 
                    ((ProcessNode) nodes.get(i).getDecorated()).getProcessType().equals(InputProcess.class)) {
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
    
    /**
     * Disposes all nodes in the network
     */
    public void dispose() {
        for (EditNodeDecorator node : nodes) {
            node.dispose();
        }
    }
    
}
