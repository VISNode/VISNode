package visnode.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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

    /** Connection size */
    private static final Dimension SIZE = new Dimension(10, 10);
    /** Connector color */
    private Color color;
    
    /**
     * Creates a new connector point
     */
    public JConnectorPoint() {
        super();
        color = Color.RED;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setPreferredSize(new Dimension(SIZE.height + 1, SIZE.width + 1));
        setMaximumSize(new Dimension(SIZE.height + 1, SIZE.width + 1));
        setMinimumSize(new Dimension(SIZE.height + 1, SIZE.width + 1));
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
        try {
            Point center = getCenter();
            Point positionInParent = getPosition(new Point(), this);
            center.translate(positionInParent.x, positionInParent.y);
            return center;
        } catch (Exception e) {
            return new Point();
        }
    }
    
    /**
     * Returns the position of a component in the topmost parent
     * 
     * @param point
     * @param component
     * @return Point
     */
    public Point getPosition(Point point, Component component) {
        point.translate(component.getX(), component.getY());
        if (component.getParent() != null && !(component.getParent() instanceof JNodeContainer)) {
            return getPosition(point, component.getParent());
        }
        return point;
    }

    @Override
    public void addPositionListener(PositionListener listener) {
        getParentNode().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                listener.positionChanged(getPosition());
            }
            
            @Override
            public void componentResized(ComponentEvent e) {
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

    /**
     * Returns the center of the connector point
     * 
     * @return Point
     */
    public Point getCenter() {
        return new Point(getWidth() / 2, getHeight() / 2);
    }

    /**
     * Returns the top-left corner of the connector point
     * 
     * @return 
     */
    private Point getTopLeftCorner() {
        Point center = getCenter();
        return new Point(center.x - (SIZE.width / 2), center.y - (SIZE.height / 2));
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        if (!isEnabled()) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        Point topLeft = getTopLeftCorner();
        g2d.fillOval(topLeft.x, topLeft.y, SIZE.width, SIZE.height);
        g2d.setColor(UIHelper.getColor("ConnectorPoint.border"));
        g2d.drawOval(topLeft.x, topLeft.y, SIZE.width, SIZE.height);
        g2d.dispose();
    }
    
}
