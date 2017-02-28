package visnode.executor;

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
    
}
