package visnode.gui;

import java.awt.event.MouseEvent;
import java.util.EventListener;

/**
 * Drag listener
 */
public interface DragListener extends EventListener {
    
    /**
     * Mouse dragged
     * 
     * @param e 
     */
    public void mouseDragged(MouseEvent e);
    
    /**
     * Drag started
     * 
     * @param e 
     */
    public void dragStarted(MouseEvent e);
    
    /**
     * Drag ended
     * 
     * @param e 
     */
    public void dragFinished(MouseEvent e);
    
}
