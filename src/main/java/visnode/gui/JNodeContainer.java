package visnode.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Node container
 */
public class JNodeContainer extends JComponent {

    /**
     * Creates a new NodeContainer
     */
    public JNodeContainer() {
        super();
        initGui();
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
     * Inicia uma conexão
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

}
