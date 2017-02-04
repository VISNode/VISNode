package visnode.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * Connector point
 */
public class JConnectorPoint extends JComponent {

    /**
     * Creates a new connector point
     */
    public JConnectorPoint() {
        super();
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setPreferredSize(new Dimension(10, 10));
        setMaximumSize(new Dimension(10, 10));
        setMinimumSize(new Dimension(10, 10));
    }

    /**
     * Returns the parent node connector
     * 
     * @return JNodeConnector
     */
    public JNodeConnector getParentNodeConnector() {
        if (getParent() instanceof JNodeConnector) {
            return (JNodeConnector) getParent();
        } else {
            throw new IllegalStateException("JNodeConnector must be inside a JNode!");
        }
    }

    /**
     * Returns the parent node
     * 
     * @return JNode
     */
    public JNode getParentNode() {
        return getParentNodeConnector().getParentNode();
    }
    
    /**
     * Returns the parent node container
     * 
     * @return JNodeContainer
     */
    public JNodeContainer getParentNodeContainer() {
        return getParentNodeConnector().getParentNodeContainer();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);
        g2d.fillOval(0, 0, 10, 10);
        g2d.dispose();
    }

}
