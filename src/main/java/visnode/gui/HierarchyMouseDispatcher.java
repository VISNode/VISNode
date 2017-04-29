package visnode.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * Mouse dispatcher for a whole hierarchy of components
 */
public class HierarchyMouseDispatcher {

    /** Instance of the singleton */
    private static HierarchyMouseDispatcher instance;
    /** Listener */
    private final List<ListenerBean> listeners;

    /**
     * Creates a new mouse dispatcher
     */
    private HierarchyMouseDispatcher() {
        listeners = new ArrayList<>();
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.addAWTEventListener((AWTEvent e) -> {
            if (e instanceof MouseEvent) {
                redirectMouseEvent((MouseEvent) e);
            }
        }, AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    /**
     * Returns the instance
     *
     * @return HierarchyMouseDispatcher
     */
    public static HierarchyMouseDispatcher get() {
        if (instance == null) {
            instance = new HierarchyMouseDispatcher();
        }
        return instance;
    }

    /**
     * Register a listener in a component
     *
     * @param component
     * @param listener
     */
    public void register(JComponent component, MouseMotionListener listener) {
        listeners.add(new ListenerBean(component, listener));
    }

    /**
     * Redirects a mouse event
     * 
     * @param evt 
     */
    private void redirectMouseEvent(MouseEvent evt) {
        for (ListenerBean listener : listeners) {
            if (isRegistered(evt, listener)) {
                redirectMouseEvent(evt, listener);
            }
        }
    }

    /**
     * Returns if the component is registered for the event
     * 
     * @param mouseEvent
     * @param listener
     * @return boolean
     */
    private boolean isRegistered(MouseEvent mouseEvent, ListenerBean listener) {
        if (!(mouseEvent.getSource() instanceof JComponent)) {
            return false;
        }
        JComponent source = (JComponent) mouseEvent.getSource();
        JComponent listen = listener.component;
        if (source.getRootPane() != listen.getRootPane()) {
            return false;
        }
        return testHierarchy(source, listen);
    }

    /**
     * Test the hierarchy between the two components
     * 
     * @param source
     * @param listen
     * @return boolean
     */
    private boolean testHierarchy(JComponent source, JComponent listen) {
        if (source == listen) {
            return true;
        }
        if (source.getParent() == null || !(source.getParent() instanceof JComponent)) {
            return false;
        }
        return testHierarchy((JComponent) source.getParent(), listen);
    }

    /**
     * Redirects the mouse event
     * @param evt
     * @param listener 
     */
    private void redirectMouseEvent(MouseEvent evt, ListenerBean listener) {
        switch(evt.getID()) {
            case MouseEvent.MOUSE_DRAGGED:
                listener.listener.mouseDragged(evt);
            case MouseEvent.MOUSE_MOVED:
                listener.listener.mouseMoved(evt);
        }
    }

    /**
     * Bean of a listener
     */
    private class ListenerBean {

        /** The component */
        final JComponent component;
        /** The listener */
        final MouseMotionListener listener;

        /**
         * Creates the bean
         *
         * @param component
         * @param listener
         */
        public ListenerBean(JComponent component, MouseMotionListener listener) {
            this.component = component;
            this.listener = listener;
        }

    }

}
