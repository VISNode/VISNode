package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Node connector
 */
public class JNodeConnector extends JComponent {

    /** Configuration of the connectors */
    private Configuration configuration;
    /** Left connector point */
    private JConnectorPoint leftConnector;
    /** Right connector point */
    private JConnectorPoint rightConnector;
    /** Component */
    private JComponent component;
    
    /**
     * Creates a new node connector
     * 
     * @param configuration
     */
    public JNodeConnector(Configuration configuration) {
        this(null, configuration);
    }

    /**
     * Creates a new node connector
     * 
     * @param component
     * @param configuration
     */
    public JNodeConnector(JComponent component, Configuration configuration) {
        super();
        this.component = component;
        this.configuration = configuration;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout(5, 0));
        add(buildLeftConnector(), BorderLayout.WEST);
        add(buildRightConnector(), BorderLayout.EAST);
        updateComponent(null);
    }

    /**
     * Builds the left connector
     * 
     * @return Component
     */
    private Component buildLeftConnector() {
        leftConnector = new JConnectorPoint();
        leftConnector.setEnabled(configuration == Configuration.LEFT || configuration == Configuration.LEFT_AND_RIGHT);
        return leftConnector;
    }

    /**
     * Builds the right connector
     * 
     * @return Component
     */
    private Component buildRightConnector() {
        rightConnector = new JConnectorPoint();
        rightConnector.setEnabled(configuration == Configuration.RIGHT || configuration == Configuration.LEFT_AND_RIGHT);
        return rightConnector;
    }

    /**
     * Returns the left connector point
     * 
     * @return  JConnectorPoint
     */
    public JConnectorPoint getLeftConnector() {
        return leftConnector;
    }

    /**
     * Returns the right connector point
     * 
     * @return JConnectorPoint
     */
    public JConnectorPoint getRightConnector() {
        return rightConnector;
    }
    
    /**
     * Returns the parent node
     * 
     * @return JNode
     */
    public JNode getParentNode() {
        if (getParent() instanceof JNode) {
            return (JNode) getParent();
        } else {
            throw new IllegalStateException("JNodeConnector must be inside a JNode!");
        }
    }
    
    /**
     * Returns the parent node container
     * 
     * @return JNodeContainer
     */
    public JNodeContainer getParentNodeContainer() {
        return getParentNode().getParentNodeContainer();
    }

    /**
     * Updates the component
     */
    private void updateComponent(Component oldComponent) {
        if (oldComponent != null) {
            remove(oldComponent);
        }
        if (component != null) {
            add(component);
        }
    }

    /**
     * Returns the connector component
     * 
     * @return JComponent
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * Sets the connector component
     * 
     * @param component 
     */
    public void setComponent(JComponent component) {
        JComponent oldValue = this.component;
        this.component = component;
        updateComponent(oldValue);
    }
    
    /**
     * Returns the configuration
     * 
     * @return Configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the configuration of the node connector
     * 
     * @param configuration 
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Configuration for the connector
     */
    public static enum Configuration {
        
        /** Connector on the left */
        LEFT,
        /** Connector on the right */
        RIGHT,
        /** Connector both on the left and on the right */
        LEFT_AND_RIGHT,
        
    }
    
}
