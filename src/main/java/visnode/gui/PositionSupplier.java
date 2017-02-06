package visnode.gui;

import java.awt.Point;

/**
 * Position supplier
 */
public interface PositionSupplier {
    
    /**
     * Returns the position
     * 
     * @return Point
     */
    public Point getPosition();
    
    /**
     * Adds a position listener
     * 
     * @param listener
     */
    public void addPositionListener(PositionListener listener);
    
}
