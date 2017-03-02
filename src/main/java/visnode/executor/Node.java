package visnode.executor;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Node representation
 */
public interface Node {

    /**
     * Returns the attribute value
     *
     * @param attribute
     * @return Object
     */
    public Object getAttribute(String attribute);

    /**
     * Adds a new parameter
     *
     * @param parameter
     * @param value
     */
    public void addParameter(String parameter, Object value);
    
    /**
     * Returns inputs parameters
     *
     * @return List
     */
    public List<NodeParameter> getInputParameters();

    /**
     * Returns outputs parameters
     *
     * @return List
     */
    public List<NodeParameter> getOutputParameters();
    
    /**
     * Returns the connector
     * 
     * @return NodeConnector
     */
    public NodeConnector getConnector();

    /**
     * Returns the name
     * 
     * @return String
     */
    public String getName();
    
    /**
     * Adds a property change listener
     * 
     * @param listener 
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);
    
}
