package visnode.gui;

import java.awt.Point;

/**
 * Position listener
 */
public interface PositionListener {
    
    /**
     * Position changed
     * 
     * @param newPosition 
     */
    public void positionChanged(Point newPosition);
    
}
