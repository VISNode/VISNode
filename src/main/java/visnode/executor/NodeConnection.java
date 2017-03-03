package visnode.executor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        
        System.out.println("conectei " + leftNode + ":" + leftAttribute + " com " + rightNode + ":" + rightAttribute);
        leftNode.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(leftAttribute)) {
                    try {
                        rightNode.executeProcess(rightAttribute);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        
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
