package visnode.executor;

/**
 * Node connection
 */
public class NodeConnection {

    /** Orin node */
    private final Node node;
    /** Attribute name */
    private final String attribute;

    /**
     * Creates a new node connection
     *
     * @param node
     * @param attribute
     */
    public NodeConnection(Node node, String attribute) {
        this.node = node;
        this.attribute = attribute;
    }

    /**
     * Returns the origin node
     *
     * @return Node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Returns the attribute name
     *
     * @return String
     */
    public String getAttribute() {
        return attribute;
    }

}
