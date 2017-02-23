package visnode.gui;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Event helper
 */
public class EventHelper {

    /**
     * Adds a component move listener
     * 
     * @param component
     * @param listener 
     */
    public static void addMoveListener(Component component, ComponentMoveListener listener) {
        component.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                listener.componentMoved(e);
            }
        });
    }
    
}
