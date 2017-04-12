
package visnode.gui;

import visnode.application.mvc.EventObject;

/**
 * Event of node connections
 */
public class NodeConnectionEvent extends EventObject {
    
    /** Left connector point */
    private final JConnectorPoint left;
    /** Right connector point */
    private final JConnectorPoint right;

    /**
     * Creates a new connection event
     * 
     * @param left
     * @param right 
     */
    public NodeConnectionEvent(JConnectorPoint left, JConnectorPoint right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left connector point
     * 
     * @return JConnectorPoint
     */
    public JConnectorPoint getLeft() {
        return left;
    }

    /**
     * Returns the right connector point
     * 
     * @return JConnectorPoint
     */
    public JConnectorPoint getRight() {
        return right;
    }
    
}
