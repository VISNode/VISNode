package visnode.executor;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Node representation
 */
public interface Node {

    /**
     * Returns a input value
     * 
     * @param attribute
     * @return Object
     */
    public Object getInput(String attribute);
    
    /**
     * Sets a input value
     * 
     * @param attribute
     * @param value 
     */
    public void setInput(String attribute, Object value);

    /**
     * Returns a output value
     * 
     * @param attribute
     * @return Object
     */
    public Object getOutput(String attribute);
    
    /**
     * Sets a output value
     * 
     * @param attribute
     * @param value 
     */
    public void setOutput(String attribute, Object value);
    
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
     * Adds a input change listener
     * 
     * @param listener 
     */
    public void addInputChangeListener(PropertyChangeListener listener);

    /**
     * Adds a input change listener
     * 
     * @param listener 
     */
    public void addOutputChangeListener(PropertyChangeListener listener);
    
}
