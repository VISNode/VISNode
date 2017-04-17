package visnode.executor;

/**
 * Node Attacher
 */
public interface AttacherNode {

    /**
     * Adds connections
     *
     * @param attribute
     * @param node
     * @param attributeNode
     */
    public void addConnection(String attribute, Node node, String attributeNode);

    /**
     * Removes connections
     *
     * @param attribute
     */
    public void removeConnection(String attribute);

    /**
     * Adds a connection change listener
     * 
     * @param listener 
     */
    public void addConnectionChangeListener(ConnectionChangeListener listener);
    
}
