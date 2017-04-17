
package visnode.gui;

import visnode.application.mvc.EventObject;

/**
 * Event of node connections
 */
public class NodeConnectionEvent extends EventObject {
    
    /** Connection */
    private final JNodeConnection connection;

    /**
     * Creates a new connection event
     * 
     * @param connection
     */
    public NodeConnectionEvent(JNodeConnection connection) {
        this.connection = connection;
    }

    /**
     * Returns the connection
     * 
     * @return JNodeConnection
     */
    public JNodeConnection getConnection() {
        return connection;
    }
    
}
