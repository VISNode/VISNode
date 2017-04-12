package visnode.gui;

import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 * Connection between a node connector point to the mouse position. Used to
 * preview the connection while dragging in JNodeContainer
 */
public class JNodeToMouseConnection extends JNodeConnection implements DragListener {

    /**
     * Container to use for the connections
     */
    private final JNodeContainer container;
    /**
     * Connector point that starts the connection
     */
    private final JConnectorPoint connectorPoint;

    /**
     * Creates a new connector-point to mouse connection
     *
     * @param container
     * @param connectorPoint
     */
    public JNodeToMouseConnection(JNodeContainer container, JConnectorPoint connectorPoint) {
        super(connectorPoint, new MousePositionSupplier(container));
        this.container = container;
        this.connectorPoint = connectorPoint;
        MouseInterceptor.get().addDragListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        getSecond().redirectEvent(e);
    }

    @Override
    public void dragStarted(MouseEvent e) {
    }

    @Override
    public void dragFinished(MouseEvent e) {
        SwingUtilities.invokeLater(() -> {
            createConnectionIfDroppedOnConnector();
            cancelMouseConnection();
            container.repaint();
        });
    }

    @Override
    public MousePositionSupplier getSecond() {
        return (MousePositionSupplier) super.getSecond();
    }

    /**
     * Creates a connection between the start point and where the user released 
     * the drag, if there's a connector there
     */
    private void createConnectionIfDroppedOnConnector() {
        JConnectorPoint endPoint = container.getConnectorPointAt(container.getMousePosition(true));
        if (endPoint != null) {
            JNodeConnection connection = container.getConnection(connectorPoint, endPoint);
            if (connection == null) {
                container.add(new JNodeConnection(connectorPoint, endPoint));
                container.fireConnectionCreated(connectorPoint, endPoint);
            } else {
                container.removeConnection(connection);
            }
        }
    }

    /**
     * Cancels this dummy connection
     */
    private void cancelMouseConnection() {
        container.remove(this);
        MouseInterceptor.get().removeDragListener(this);
    }

}
