
package visnode.executor;

import java.util.EventObject;

/**
 * Event for connection changes
 */
public class ConnectionChangeEvent extends EventObject {

    /** Node parameter */
    private final NodeParameter parameter;
    /** Event type */
    private final EventType type;
    
    /**
     * Creates a new connection change event
     * 
     * @param source 
     */
    public ConnectionChangeEvent(NodeParameter parameter, EventType type, Object source) {
        super(source);
        this.parameter = parameter;
        this.type = type;
    }

    /**
     * Returns the parameter
     * 
     * @return NodeParameter
     */
    public NodeParameter getParameter() {
        return parameter;
    }

    /**
     * Returns the event type
     * 
     * @return EventType
     */
    public EventType getType() {
        return type;
    }

    /**
     * Returns if the event is of a creating type
     * 
     * @return boolean
     */
    public boolean isCreating() {
        return type == EventType.CREATE;
    }
    
    /**
     * Event type
     */
    public static enum EventType {
        CREATE,
        REMOVE
    }

}
