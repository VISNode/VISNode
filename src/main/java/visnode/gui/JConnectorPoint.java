package visnode.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 * Connector point
 */
public class JConnectorPoint extends JComponent implements PositionSupplier {

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
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                getParentNodeContainer().startConnection(JConnectorPoint.this, e);
            }
           
        });
        
        
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
     * Connect this connector to another connector
     * 
     * @param another 
     */
    public void connectTo(JConnectorPoint another) {
        getParentNodeContainer().add(new JNodeConnection(this, another));
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
    public Point getPosition() {
        return new Point(getParentNode().getX() + getX(), getParentNode().getY() + getY());
    }

    @Override
    public void addPositionListener(PositionListener listener) {
        getParentNode().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                listener.positionChanged(getPosition());
            }

            @Override
            public void componentShown(ComponentEvent e) {
                listener.positionChanged(getPosition());
            }

        });
        getParentNode().addHierarchyListener((HierarchyEvent e) -> {
            listener.positionChanged(getPosition());
        });
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
