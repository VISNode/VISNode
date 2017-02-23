package visnode.gui;

import java.awt.event.ComponentEvent;
import java.util.EventListener;

/**
 * COmponent resize listener
 */
public interface ComponentMoveListener extends EventListener {

    /**
     * Invoked when the component's position changes
     * 
     * @param e
     */
    public void componentMoved(ComponentEvent e);

}
