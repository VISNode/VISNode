package visnode.executor;

import io.reactivex.Observable;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import visnode.commons.DynamicValue;

/**
 * Output node representation
 */
public class OutputNode implements Node, AttacherNode {

    /** Node connector */
    private final NodeConnector connector;
    /** Property change support */
    private final PropertyChangeSupport propertyChangeSupport;
    /** Listeners list */
    private final EventListenerList listenerList;
    /** Value */
    private DynamicValue value;
    
    /**
     * Creates a new output node
     */
    public OutputNode() {
        this.connector = new NodeConnector(this);
        propertyChangeSupport = new PropertyChangeSupport(this);
        listenerList = new EventListenerList();
        value = null;
    }

    @Override
    public Object getInput(String attribute) {
        if (attribute.equals("value")) {
            return value;
        }
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public void setInput(String attribute, Object value) {
        if (attribute.equals("value")) {
            this.value = new DynamicValue(value);
            propertyChangeSupport.firePropertyChange(attribute, null, value);
            return;
        }
        throw new InvalidAttributeException(attribute);
    }
    
    /**
     * Returns the output image
     * 
     * @return DynamicValue
     */
    public DynamicValue getValue() {
        return value;
    }
    
    @Override
    public Observable getOutput(String attribute) {
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public void setOutput(String attribute, Object value) {
        throw new InvalidAttributeException(attribute);
    }
    
    @Override
    public void addConnection(final String attribute, Node node, String attributeNode) {
        connector.addConnection(attribute, node, attributeNode);
    }

    @Override
    public void removeConnection(String attribute) {
        connector.removeConnection(attribute);
    }

    @Override
    public List<NodeParameter> getInputParameters() {
        List<NodeParameter> list = new ArrayList<>();
        list.add(new NodeParameter("value", DynamicValue.class));
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
    public void addInputChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    @Override
    public void addOutputChangeListener(PropertyChangeListener listener) {
    }
    
    @Override
    public void removeOutputChangeListener(PropertyChangeListener listener) {
    }

    @Override
    public String getName() {
        return "Output";
    }

    @Override
    public void addConnectionChangeListener(ConnectionChangeListener listener) {
        listenerList.add(ConnectionChangeListener.class, listener);
    }

}
