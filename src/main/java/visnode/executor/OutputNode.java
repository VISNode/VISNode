package visnode.executor;

import visnode.commons.Image;

/**
 * Output node representation
 */
public class OutputNode implements Node, AttacherNode {

    /** Node connector */
    private final NodeConnector connector;

    /**
     * Creates a new output node
     */
    public OutputNode() {
        this.connector = new NodeConnector();
    }

    @Override
    public Object getAttribute(String attribute) {
        if (attribute.equals("image")) {
            return execute();
        }
        return null;
    }

    @Override
    public void addConnection(String attribute, Node node, String attributeNode) {
        connector.addConnection(attribute, node, attributeNode);
    }

    /**
     * Executes process
     *
     * @return Image
     */
    private Image execute() {
        return (Image) connector.getConnection("image").getNode().getAttribute("image");
    }

}
