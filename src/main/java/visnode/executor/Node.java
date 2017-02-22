package visnode.executor;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<String> getInputParameters();

    /**
     * Returns outputs parameters
     *
     * @return List
     */
    public List<String> getOutputParameters();
    
    /**
     * Returns the connector
     * 
     * @return NodeConnector
     */
    public NodeConnector getConnector();
    
}
