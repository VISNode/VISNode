package visnode.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComponent;

/**
 * Node container
 */
public class JNodeContainer extends JComponent {

    /** Selection of nodes */
    private final Selection<JNode> selection;
    /** Select listener */
    private final SelectListener selectListener;
    /** Revalidation listener */
    private final RevalidationListener revalidationListener;
    
    /**
     * Creates a new NodeContainer
     */
    public JNodeContainer() {
        super();
        this.selection = new Selection<>();
        this.selectListener = new SelectListener();
        this.revalidationListener = new RevalidationListener();
        initGui();
        registerListeners();
    }

    /**
     * Inititalizes the interface
     */
    private void initGui() {
        setLayout(null);
        setupDragSupport();
    }

    /**
     * Sets up the drag support
     */
    private void setupDragSupport() {
        DragSupport dragSupport = new DragSupport(this);
        dragSupport.setAllowDragPredicate((component) -> component instanceof JNode || component instanceof JConnectorPoint);
    }

    /**
     * Register the listeners
     */
    private void registerListeners() {
        addContainerListener(new ContainerListener() {
            @Override
            public void componentAdded(ContainerEvent e) {
                if (e.getChild() instanceof JNode) {
                    e.getChild().addMouseListener(selectListener);
                    e.getChild().addComponentListener(revalidationListener);
                }
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                if (e.getChild() instanceof JNode) {
                    e.getChild().removeMouseListener(selectListener);
                    e.getChild().removeComponentListener(revalidationListener);
                }
            }
        });
    }
    
    /** 
     * Starts a connection
     * 
     * @param connectorPoint
     * @param e 
     */
    public void startConnection(JConnectorPoint connectorPoint, MouseEvent e) {
        add(new JNodeToMouseConnection(this, connectorPoint));
    }

    /**
     * Returns the connector point in a specified position
     * 
     * @param point
     * @return JConnectorPoint
     */
    public JConnectorPoint getConnectorPointAt(Point point) {
        return getConnectorPointAtRecursive(this, point);
    }

    /**
     * Returns the connector point in a specified position
     * 
     * @param point
     * @return JConnectorPoint
     */
    private JConnectorPoint getConnectorPointAtRecursive(Component parent, Point point) {
        point = new Point(point);
        point.translate(-parent.getX(), -parent.getY());
        Component at = parent.getComponentAt(point);
        if (at instanceof JConnectorPoint) {
            return (JConnectorPoint) at;
        }
        if (at instanceof JNode || at instanceof JNodeConnector) {
            return getConnectorPointAtRecursive(at, point);
        }
        return null;
    }

    /**
     * Returns the connection between two connector points, or {@code null} if
     * none.
     * 
     * @param start
     * @param end
     * @return JNodeConnection
     */
    public JNodeConnection getConnection(JConnectorPoint start, JConnectorPoint end) {
        for (int i = 0; i < getComponentCount(); i++) {
            Component child = getComponent(i);
            if (child instanceof JNodeConnection) {
                JNodeConnection connection = (JNodeConnection) child;
                if ((connection.getFirst() == start || connection.getSecond() == start) && 
                    (connection.getFirst() == end || connection.getSecond() == end)) {
                    return connection;
                }
            }
        }
        return null;
    }
    
    /**
     * Returns the connections of a node
     * 
     * @param node
     * @return Set of JNodeConnection
     */
    public Set<JNodeConnection> getConnections(JNode node) {
        Set<JNodeConnector> connectors = node.getConnectors();
        Set<JNodeConnection> connections = new HashSet<>();
        for (int i = 0; i < getComponentCount(); i++) {
            Component child = getComponent(i);
            if (child instanceof JNodeConnection) {
                JNodeConnection connection = (JNodeConnection) child;
                for (JNodeConnector connector : connectors) {
                    if (connection.getFirst() == connector.getLeftConnector() || connection.getSecond() == connector.getLeftConnector() ||
                        connection.getFirst() == connector.getRightConnector() || connection.getSecond() == connector.getRightConnector()) {
                        connections.add(connection);
                        break;
                    }
                }
            }
        }
        return connections;
    }

    /**
     * Removes a connection
     * 
     * @param connection 
     */
    public void removeConnection(JNodeConnection connection) {
        remove(connection);
    }
    
    /**
     * Adds a connection listener
     * 
     * @param listener 
     */
    public void addNodeConnectionListener(NodeConnectionListener listener) {
       listenerList.add(NodeConnectionListener.class, listener);
    }
    
    /**
     * Fires the event of a connection created
     * 
     * @param connection
     */
    public void fireConnectionCreated(JNodeConnection connection) {
        for (NodeConnectionListener listener : listenerList.getListeners(NodeConnectionListener.class)) {
            listener.connectionCreated(new NodeConnectionEvent(connection));
        }
    }
    
    /**
     * Fires the event of a connection removed
     * 
     * @param connection
     */
    public void fireConnectionRemoved(JNodeConnection connection) {
        for (NodeConnectionListener listener : listenerList.getListeners(NodeConnectionListener.class)) {
            listener.connectionRemoved(new NodeConnectionEvent(connection));
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int maxX = super.getPreferredSize().width;
        int maxY = super.getPreferredSize().height;
        for (Component component : getComponents()) {
            maxX = Math.max(maxX, component.getX() + component.getWidth());
            maxY = Math.max(maxY, component.getY() + component.getHeight());
        }
        return new Dimension(maxX, maxY);
    }

    /**
     * Returns the selection
     * 
     * @return {@code Selection<JNode>}
     */
    public Selection<JNode> getSelection() {
        return selection;
    }
    
    /**
     * Select listener
     */
    private class SelectListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() instanceof JNode) {
                JNode node = (JNode) e.getSource();
                Selection<JNode> oldSelection = selection.copy();
                for (JNode oldNode : oldSelection) {
                    oldNode.repaint();
                }
                selection.set(node);
                node.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
    /**
     * Listener that revalidates the preferred size when the children change
     */
    private class RevalidationListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            revalidate();
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            revalidate();
        }

        @Override
        public void componentShown(ComponentEvent e) {
            revalidate();
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            revalidate();
        }
    
    }

}
