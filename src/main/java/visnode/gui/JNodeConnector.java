package visnode.gui;

import java.awt.BorderLayout;
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
    
    /**
     * Creates a new node connector
     * 
     * @param configuration
     */
    public JNodeConnector(Configuration configuration) {
        super();
        this.configuration = configuration;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(new JLabel("Connector"));
        leftConnector = new JConnectorPoint();
        add(leftConnector, BorderLayout.WEST);
        rightConnector = new JConnectorPoint();
        add(rightConnector, BorderLayout.EAST);
        leftConnector.setVisible(configuration == Configuration.LEFT || configuration == Configuration.LEFT_AND_RIGHT);
        rightConnector.setVisible(configuration == Configuration.RIGHT || configuration == Configuration.LEFT_AND_RIGHT);
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
