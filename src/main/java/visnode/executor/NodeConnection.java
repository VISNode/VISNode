package visnode.executor;

import java.beans.PropertyChangeEvent;
import visnode.application.ExceptionHandler;

/**
 * Node connection
 */
public class NodeConnection {

    /** Orin leftNode */
    private final Node leftNode;
    /** Attribute name */
    private final String leftAttribute;
    /** Orin leftNode */
    private final Node rightNode;
    /** Attribute name */
    private final String rightAttribute;

    /**
     * Creates a new leftNode connection
     *
     * @param node
     * @param attribute
     */
    public NodeConnection(Node leftNode, String leftAttribute, Node rightNode, String rightAttribute) {
        this.leftNode = leftNode;
        this.leftAttribute = leftAttribute;
        this.rightNode = rightNode;
        this.rightAttribute = rightAttribute;
        leftNode.addOutputChangeListener((PropertyChangeEvent evt) -> {
            if (evt.getPropertyName().equals(leftAttribute)) {
                try {
                    rightNode.setInput(rightAttribute, evt.getNewValue());
                } catch(Exception e) {
                    ExceptionHandler.get().handle(e);
                }
            }
        });
        try {
            rightNode.setInput(rightAttribute, leftNode.getOutput(leftAttribute));
        } catch(Exception e) {
            ExceptionHandler.get().handle(e);
        }   
    }

    /**
     * Returns the origin leftNode
     *
     * @return Node
     */
    public Node getLeftNode() {
        return leftNode;
    }

    /**
     * Returns the leftAttribute name
     *
     * @return String
     */
    public String getLeftAttribute() {
        return leftAttribute;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public String getRightAttribute() {
        return rightAttribute;
    }

}
