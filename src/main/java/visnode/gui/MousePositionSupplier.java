package visnode.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * Supplier of position based on the mouse
 */
public class MousePositionSupplier implements PositionSupplier {

    /** Parent of the mouse position supplier */
    private final JComponent parent;
    /** Listeners */
    private final List<PositionListener> listeners;

    /**
     * Creates a new mouse position supplier
     * 
     * @param parent
     */
    public MousePositionSupplier(JComponent parent) {
        this.parent = parent;
        this.listeners = new ArrayList<>();
    }
    
    /**
     * Redirects the event for the position listeners
     * 
     * @param e 
     */
    public void redirectEvent(MouseEvent e) {
        for (PositionListener listener : listeners) {
            listener.positionChanged(getPosition());
        }
    }
    
    @Override
    public Point getPosition() {
        return parent.getMousePosition();
    }

    @Override
    public void addPositionListener(PositionListener listener) {
        listeners.add(listener);
    }
    
}
