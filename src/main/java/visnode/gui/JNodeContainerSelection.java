package visnode.gui;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JComponent;

/**
 * Node container selection
 */
public class JNodeContainerSelection extends JComponent {

    /** Initial selection */
    private final Point initialSelection;
    /** Final selection */
    private Point finalSelection;

    /**
     * Creates a new node container selection
     * 
     * @param initialSelection 
     */
    public JNodeContainerSelection(Point initialSelection) {
        super();
        this.initialSelection = initialSelection;
    }

    /**
     * Updates the selection
     * 
     * @param finalSelection 
     */
    public void update(Point finalSelection) {
        this.finalSelection = finalSelection;
        int x = initialSelection.x;
        int y = initialSelection.y;
        if (finalSelection.x < x) {
            x = finalSelection.x;
        }
        if (finalSelection.y < y) {
            y = finalSelection.y;
        }
        setBounds(x, y,
                Math.abs(finalSelection.x - initialSelection.x),
                Math.abs(finalSelection.y - initialSelection.y));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (initialSelection != null && finalSelection != null) {
            Dimension dim = getSize();
            dim.width--;
            dim.height--;
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f));
            g2.draw(new Rectangle(dim));

        }
    }

    /**
     * Returns the initial selection
     * 
     * @return Point
     */
    public Point getInitialSelection() {
        return initialSelection;
    }

    /**
     * Returns the final selection
     * 
     * @return Point
     */
    public Point getFinalSelection() {
        return finalSelection;
    }

    /**
     * Returns true if selectio contains a component
     * 
     * @param component
     * @return boolean
     */
    public boolean contains(Component component) {
        int x = component.getX() - getX();
        int y = component.getY() - getY();
        return contains(x, y)
                || contains(x + component.getWidth(), y)
                || contains(x, y + component.getHeight())
                || contains(x + component.getWidth(), y + component.getHeight());
    }

}
