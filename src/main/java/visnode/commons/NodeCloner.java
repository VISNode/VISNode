package visnode.commons;

import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.NodeParameter;
import visnode.executor.ProcessNode;

/**
 * Utility class for cloning nodes
 */
public class NodeCloner {

    /** Base node */
    private final Node baseNode;
    /** Clone */
    private Node clone;

    /**
     * Creates a new cloner
     *
     * @param baseNode
     */
    public NodeCloner(Node baseNode) {
        this.baseNode = baseNode;
    }

    /**
     * Performs a full clone
     * 
     * @return NodeCloner
     */
    public NodeCloner fullClone() {
        cloneProcess();
        cloneInputs();
        return this;
    }

    /**
     * Clone the node as a process node
     * 
     * @return NodeCloner
     */
    public NodeCloner cloneProcess() {
        ProcessNode processNode = findProcessNode();
        Class claz = processNode.getProcessType();
        clone = new ProcessNode(claz);
        return this;
    }

    /**
     * Clones the input of a process node
     * 
     * @return NodeCloner
     */
    public NodeCloner cloneInputs() {
        ProcessNode process = findProcessNode();
        for (NodeParameter inputParameter : process.getInputParameters()) {
            String name = inputParameter.getName();
            if (process.getConnector().getConnection(name) == null) {
                clone.setInput(name, process.getInput(name));
            }
        }
        return this;
    }

    /**
     * Creates an edit node
     * 
     * @return EditNodeDecorator
     */
    public EditNodeDecorator createEditNode() {
        return new EditNodeDecorator(clone, findEditNodeDecorator().getPosition());
    }
    
    /**
     * Find the process node
     * 
     * @return ProcessNode
     */
    private ProcessNode findProcessNode() {
        return (ProcessNode) findDecorated();
    }
    
    /**
     * Finds the decorated node
     * 
     * @return Node
     */
    private Node findDecorated() {
        if (baseNode instanceof EditNodeDecorator) {
            return findEditNodeDecorator().getDecorated();
        }
        return baseNode;
    }
    
    /**
     * Finds the edit node decorator
     * 
     * @return EditNodeDecorator
     */
    private EditNodeDecorator findEditNodeDecorator() {
        return (EditNodeDecorator) baseNode;
    }

}
