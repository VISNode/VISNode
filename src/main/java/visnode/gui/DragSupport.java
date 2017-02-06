package visnode.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.function.Predicate;
import javax.swing.JComponent;

/**
 * Drag support for component
 */
public class DragSupport implements MouseListener, MouseMotionListener {
    
    /** Container */
    private final JComponent container;
    /** Component that is being dragged */
    private Component dragged;
    /** Relative position in the dragged component */
    private Point relativePosition;
    /** Predicate for allowing the drag */
    private Predicate<Component> allowDragPredicate;

    /**
     * Creates a new drag support for a container
     * 
     * @param container 
     */
    public DragSupport(JComponent container) {
        this.container = container;
        registerListeners();
    }

    /**
     * Register the listeners
     */
    private void registerListeners() {
        container.addMouseListener(this);
        container.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragged = container.getComponentAt(e.getPoint());
        if (dragged == container || !allowDragPredicate.test(dragged)) {
            dragged = null;
            return;
        }
        relativePosition = new Point(e.getPoint());
        relativePosition.translate(-dragged.getX(), -dragged.getY());
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

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragged != null) {
            dragged.setLocation(e.getPoint().x - relativePosition.x, e.getPoint().y - relativePosition.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    /**
     * Returns the predicate for allowing the drag of components
     * 
     * @return {@code Predicate<Component>}
     */
    public Predicate<Component> getAllowDragPredicate() {
        return allowDragPredicate;
    }

    /**
     * Sets the predicate for allowing the drag of components
     * 
     * @param allowDragPredicate 
     */
    public void setAllowDragPredicate(Predicate<Component> allowDragPredicate) {
        this.allowDragPredicate = allowDragPredicate;
    }
    
}
