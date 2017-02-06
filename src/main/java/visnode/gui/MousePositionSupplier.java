package visnode.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.JComponent;

/**
 * Supplier of position based on the mouse
 */
public class MousePositionSupplier implements PositionSupplier {

    /** Parent of the mouse position supplier */
    private final JComponent parent;
    /** Predicate for updating the position */
    private final Predicate<MouseEvent> updatePredicate;
    /** Listeners */
    private final List<PositionListener> listeners;

    /**
     * Creates a new mouse position supplier
     * 
     * @param parent
     * @param updatePredicate 
     */
    public MousePositionSupplier(JComponent parent, Predicate<MouseEvent> updatePredicate) {
        this.parent = parent;
        this.updatePredicate = updatePredicate;
        this.listeners = new ArrayList<>();
        parent.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                redirectEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                redirectEvent(e);
            }

        });
    }
    
    /**
     * Redirects the event for the position listeners
     * 
     * @param e 
     */
    private void redirectEvent(MouseEvent e) {
        if (updatePredicate.test(e)) {
            for (PositionListener listener : listeners) {
                listener.positionChanged(getPosition());
            }
        }
    }
    
    @Override
    public Point getPosition() {
        System.out.println("bump");
        return parent.getMousePosition();
    }

    @Override
    public void addPositionListener(PositionListener listener) {
        listeners.add(listener);
    }
    
}
