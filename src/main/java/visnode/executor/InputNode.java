package visnode.executor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.application.ExceptionHandler;
import visnode.application.InputReader;

/**
 * Input node representation
 */
public class InputNode implements Node {

    /** Node connector */
    private final NodeConnector connector;
    /** Input image */
    private Image image;
    /** Image file */
    private File file;
    /** PropertyChangeSupport for outputs */
    private final PropertyChangeSupport outputChangeSupport;

    /**
     * Creates a new input node
     */
    public InputNode() {
        this.image = ImageFactory.buildEmptyImage();
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

    @Override
    public void removeOutputChangeListener(PropertyChangeListener listener) {
        outputChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Sets the image
     *
     * @param image 
     */
    public void setImage(Image image) {
        Image oldValue = this.image;
        this.image = image;
        this.file = null;
        outputChangeSupport.firePropertyChange("image", oldValue, image);
    }

    /**
     * Sets the file
     *
     * @param file
     */
    public void setFile(File file) {
        Image oldValue = this.image;
        try {
            this.file = file;
            this.image = new InputReader().read(file);
        } catch (IOException e) {
            ExceptionHandler.get().handle(e);
        }
        outputChangeSupport.firePropertyChange("image", oldValue, image);
    }

    /**
     * Returns the input file
     * 
     * @return File
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Returns the image
     *
     * @return Image
     */
    public Image getImage() {
        return image;
    }

    @Override
    public String getName() {
        return "Input";
    }

}
