package visnode.executor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import visnode.commons.Image;

/**
 * Input node representation
 */
public class InputNode implements Node {

    /** Node connector */
    private final NodeConnector connector;
    /** Input image */
    private Image image;
    /** PropertyChangeSupport for outputs */
    private final PropertyChangeSupport outputChangeSupport;

    /**
     * Creates a new input node
     *
     * @param image
     */
    public InputNode(Image image) {
        this.image = image;
        this.connector = new NodeConnector(this);
        outputChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public Object getInput(String attribute) {
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public void setInput(String attribute, Object value) {
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public Object getOutput(String attribute) {
        if (attribute.equals("image")) {
            return image;
        }
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public void setOutput(String attribute, Object value) {
        if (attribute.equals("image")) {
            throw new IllegalStateException("Image is final and cannot be modified");
        }
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public List<NodeParameter> getInputParameters() {
        return new ArrayList<>();
    }

    @Override
    public List<NodeParameter> getOutputParameters() {
        List<NodeParameter> list = new ArrayList<>();
        list.add(new NodeParameter("image", Image.class));
        return list;
    }

    @Override
    public NodeConnector getConnector() {
        return connector;
    }
    
    @Override
    public void addInputChangeListener(PropertyChangeListener listener) {
    }

    @Override
    public void addOutputChangeListener(PropertyChangeListener listener) {
        outputChangeSupport.addPropertyChangeListener(listener);
    }
    
    /**
     * Sets the image
     * 
     * @param image 
     */
    public void setImage(Image image) {
        Image oldValue = this.image;
        this.image = image;
        outputChangeSupport.firePropertyChange("image", oldValue, image);
    }

    @Override
    public String getName() {
        return "Input";
    }

}
