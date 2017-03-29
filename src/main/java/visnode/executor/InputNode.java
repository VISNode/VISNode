package visnode.executor;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import visnode.commons.Image;

/**
 * Input node representation
 */
public class InputNode implements Node {

    /** Input image */
    private final Image imagem;
    /** Node connector */
    private final NodeConnector connector;

    /**
     * Creates a new input node
     *
     * @param imagem
     */
    public InputNode(Image imagem) {
        this.imagem = imagem;
        this.connector = new NodeConnector(this);
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
            return imagem;
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
    }

    @Override
    public String getName() {
        return "Input";
    }

}
