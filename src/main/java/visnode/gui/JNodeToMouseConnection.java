package visnode.gui;

import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;

/**
 * Connection between a node connector point to the mouse position. Used to
 * preview the connection while dragging in JNodeContainer
 */
public class JNodeToMouseConnection extends JNodeConnection implements DragListener {

    /** Container to use for the connections */
    private final JNodeContainer container;
    /** Connector point that starts the connection */
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
            try {
                createConnectionIfDroppedOnConnector();
                cancelMouseConnection();
                container.repaint();
            } catch (RuntimeException ex) {
                ExceptionHandler.get().handle(ex);
            }
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
        if (endPoint != null && endPoint != connectorPoint) {
            JNodeConnection connection = container.getConnection(connectorPoint, endPoint);
            if (connection == null) {
                connection = new JNodeConnection(connectorPoint, endPoint);
                container.add(connection);
                try {
                    container.fireConnectionCreated(connection);
                } catch (IllegalArgumentException e) {
                    container.removeConnection(connection);
                }
            } else {
                container.removeConnection(connection);
                container.fireConnectionRemoved(connection);
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
