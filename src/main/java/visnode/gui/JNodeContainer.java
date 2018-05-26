package visnode.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;

/**
 * Node container
 */
public class JNodeContainer extends JLayeredPane {

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
     * Initializes the interface
     */
    private void initGui() {
        setLayout(null);
        setupDragSupport();
        setFocusable(true);
    }

    /**
     * Sets up the drag support
     */
    private void setupDragSupport() {
        DragSupport dragSupport = new DragSupport(this);
        dragSupport.setAllowDragPredicate((component) -> component instanceof JNode || component instanceof JConnectorPoint);
        dragSupport.setSelection(() -> {
            return selection.stream().map((e) -> (JComponent) e).collect(Collectors.toList());
        });
    }

    @Override
    public Component add(Component comp) {
        if (comp instanceof JNodeConnection) {
            super.add(comp, DEFAULT_LAYER);
            return comp;
        }
        if (comp instanceof JNode) {
            super.add(comp, PALETTE_LAYER);
            return comp;
        }
        throw new IllegalArgumentException("Only JNodes and JConnections can be added to the container");
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
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
                clearSelection();
            }
        });
        MouseInterceptor.get().addDragListener(new DragSelectionListener());
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
        Component at = parent.getComponentAt(point);
        if (at instanceof JConnectorPoint) {
            return (JConnectorPoint) at;
        }
        if (at instanceof JNode || at instanceof JNodeConnector) {
            point = new Point(point);
            point.translate(-at.getX(), -at.getY());
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
                if ((connection.getFirst() == start || connection.getSecond() == start)
                        && (connection.getFirst() == end || connection.getSecond() == end)) {
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
                    if (connection.getFirst() == connector.getLeftConnector()
                            || connection.getSecond() == connector.getLeftConnector()
                            || connection.getFirst() == connector.getRightConnector()
                            || connection.getSecond() == connector.getRightConnector()) {
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

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        super.paintComponent(g2d);
        int gridSize = 10;
        int majorGridSize = 100;
        g2d.setColor(UIHelper.getColor("Node.container.c1"));
        for (int i = 0; i < Math.max(getWidth(), getHeight()); i += gridSize) {
            if (i < getWidth()) {
                g2d.drawLine(i, 0, i, getHeight());
            }
            if (i < getHeight()) {
                g2d.drawLine(0, i, getWidth(), i);
            }
        }
        g2d.setColor(UIHelper.getColor("Node.container.c2"));
        for (int i = 0; i < Math.max(getWidth(), getHeight()); i += majorGridSize) {
            if (i < getWidth()) {
                g2d.drawLine(i, 0, i, getHeight());
            }
            if (i < getHeight()) {
                g2d.drawLine(0, i, getWidth(), i);
            }
        }
        g2d.dispose();
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
     * Clears the selecion
     */
    public void clearSelection() {
        for (JNode oldNode : selection.copy()) {
            oldNode.repaint();
        }
        selection.clear();
    }

    /**
     * Select listener
     */
    private class SelectListener implements MouseListener {

        /** The point where it was pressed */
        private Point pressedPosition;
        /** The old selection */
        private Selection<JNode> oldSelection;

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() instanceof JNode) {
                pressedPosition = e.getLocationOnScreen();
                oldSelection = selection.copy();
                JNode node = (JNode) e.getSource();
                // Add new node to selection
                if (e.isControlDown()) {
                    if (!selection.contains(node)) {
                        selection.add(node);
                    }
                    node.repaint();
                    return;
                }
                // it has only one selection
                if (selection.isEmpty() || selection.size() == 1) {
                    for (JNode oldNode : oldSelection) {
                        oldNode.repaint();
                    }
                    selection.set(node);
                    node.repaint();
                }
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getSource() instanceof JNode) {
                // If there weren't multiple selection or component has draged
                if (!e.isControlDown() || !e.getLocationOnScreen().equals(pressedPosition)) {
                    return;
                }
                JNode node = (JNode) e.getSource();
                // It is a new node
                if (!oldSelection.contains(node)) {
                    return;
                }
                selection.remove(node);
                node.repaint();
            }
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

    /**
     * Drag selection listener
     */
    private class DragSelectionListener implements DragListener {

        /** Container selection */
        private JNodeContainerSelection selectionContainer;

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectionContainer != null) {
                selectionContainer.update(e.getPoint());
            }
        }

        @Override
        public void dragStarted(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() instanceof JLayeredPane) {
                selectionContainer = new JNodeContainerSelection(e.getPoint());
                add(selectionContainer, JLayeredPane.POPUP_LAYER);
            }
        }

        @Override
        public void dragFinished(MouseEvent e) {
            if (selectionContainer != null) {
                remove(selectionContainer);
                repaint();
                selection.clear();
                for (Component component : getComponents()) {
                    // Selection contains the component
                    if (component instanceof JNode && selectionContainer.contains(component)) {
                        selection.add((JNode) component);
                    }
                }
                selectionContainer = null;
            }
        }
    }

}
