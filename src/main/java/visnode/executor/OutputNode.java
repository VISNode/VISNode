package visnode.executor;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
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
    public void addParameter(String parameter, Object value) {
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
    

    @Override
    public List<NodeParameter> getInputParameters() {
        List<NodeParameter> list = new ArrayList<>();
        list.add(new NodeParameter("image", Image.class));
        return list;
    }

    @Override
    public List<NodeParameter> getOutputParameters() {
        return new ArrayList<>();
    }

    @Override
    public NodeConnector getConnector() {
        return connector;
    }
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

}
