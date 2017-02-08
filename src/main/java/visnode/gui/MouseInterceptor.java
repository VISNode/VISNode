package visnode.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.event.EventListenerList;

/**
 * Application-wise mouse dispatcher. Listen to mouse events anywhere in the
 * application, and is able to redirect some of this events to listeners
 */
public class MouseInterceptor implements AWTEventListener {

    /** Singleton instance */
    private static MouseInterceptor instance;
    /** */
    private final EventListenerList listenerList;

    /**
     * Creates a new mouse interceptor
     */
    public MouseInterceptor() {
        this.listenerList = new EventListenerList();
    }

    /**
     * Returns the default implementation of the dispatcher
     * 
     * @return MouseInterceptor
     */
    public static MouseInterceptor get() {
        if (instance == null) {
            instance = new MouseInterceptor();
            Toolkit.getDefaultToolkit().addAWTEventListener(instance, AWTEvent.MOUSE_EVENT_MASK);
            Toolkit.getDefaultToolkit().addAWTEventListener(instance, AWTEvent.MOUSE_MOTION_EVENT_MASK);
            Toolkit.getDefaultToolkit().addAWTEventListener(instance, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        }
        return instance;
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {
            fireDragEvent((MouseEvent) event);
        }
    }
    
    /**
     * Adds a listener
     * 
     * @param listener 
     */
    public void addDragListener(DragListener listener) {
        listenerList.add(DragListener.class, listener);
    }
    
    /**
     * Removes a listener
     * 
     * @param listener 
     */
    public void removeDragListener(DragListener listener) {
        listenerList.remove(DragListener.class, listener);
    }

    /**
     * Fires a drag event
     * 
     * @param event 
     */
    private void fireDragEvent(MouseEvent event) {
        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            for (DragListener listener : listenerList.getListeners(DragListener.class)) {
                listener.dragStarted(event);
            }
        }
        if (event.getID() == MouseEvent.MOUSE_RELEASED) {
            for (DragListener listener : listenerList.getListeners(DragListener.class)) {
                listener.dragFinished(event);
            }
        }
        if (event.getID() == MouseEvent.MOUSE_DRAGGED) {
            for (DragListener listener : listenerList.getListeners(DragListener.class)) {
                listener.mouseDragged(event);
            }
        }
    }

}
