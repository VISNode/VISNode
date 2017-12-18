package visnode.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import javax.swing.JComponent;
import visnode.application.ExceptionHandler;

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
    /** Component selection */
    private Callable<List<JComponent>> selection;

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
        container.addContainerListener(new ContainerListener() {
            @Override
            public void componentAdded(ContainerEvent e) {
                if (allowDragPredicate.test(e.getChild())) {
                    e.getChild().addMouseListener(DragSupport.this);
                    e.getChild().addMouseMotionListener(DragSupport.this);
                }
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                if (allowDragPredicate.test(e.getChild())) {
                    e.getChild().removeMouseListener(DragSupport.this);
                    e.getChild().removeMouseMotionListener(DragSupport.this);
                }
            }
        });

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragged = (Component) e.getSource();
        relativePosition = new Point(dragged.getX(), dragged.getY());
        relativePosition.translate(-e.getLocationOnScreen().x, -e.getLocationOnScreen().y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragged = null;
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
            Point position = e.getLocationOnScreen();
            position.translate(relativePosition.x, relativePosition.y);
            int diffX = position.x - dragged.getX();
            int diffY = position.y - dragged.getY();
            dragged.setLocation(position.x, position.y);
            try {
                selection.call().stream().
                        filter((it) -> it != dragged).
                        forEach((sel) -> {
                            Point location = sel.getLocation();
                            location.translate(diffX, diffY);
                            sel.setLocation(location);
                        });
            } catch (Exception ex) {
                ExceptionHandler.get().handle(ex);
            }
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

    /**
     * Define the component selection
     * 
     * @param selection 
     */
    public void setSelection(Callable<List<JComponent>> selection) {
        this.selection = selection;
    }

}
