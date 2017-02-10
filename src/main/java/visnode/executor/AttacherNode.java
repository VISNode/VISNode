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

}
