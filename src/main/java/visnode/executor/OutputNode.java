package visnode.executor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import visnode.commons.Image;

/**
 * Output node representation
 */
public class OutputNode implements Node, AttacherNode {

    /** Node connector */
    private final NodeConnector connector;
    /** Property change support */
    private final PropertyChangeSupport propertyChangeSupport;

    private Image img;
    
    /**
     * Creates a new output node
     */
    public OutputNode() {
        this.connector = new NodeConnector(this);
        propertyChangeSupport = new PropertyChangeSupport(this);
        
        img = null;
        
    }

    @Override
    public Object getParameter(String attribute) {
        if (attribute.equals("image")) {
            return execute();
        }
        return null;
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
        execute();
        propertyChangeSupport.firePropertyChange(parameter, null, value);
    }

    @Override
    public void addConnection(final String attribute, Node node, String attributeNode) {
        connector.addConnection(attribute, node, attributeNode);
    }

    /**
     * Executes process
     *
     * @return Image
     */
    private Image execute() {
        if (img == null) {
            img = (Image) connector.getConnection("image").getLeftNode().getAttribute("image");
            propertyChangeSupport.firePropertyChange("image", null, img);
        }
        return img;
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
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    @Override
    public visnode.pdi.Process executeProcess(String attribute) throws Exception {
        img = null;
        execute();
        return null;
    }

    @Override
    public String getName() {
        return "Output";
    }

}
