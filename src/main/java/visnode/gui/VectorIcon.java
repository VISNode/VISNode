
package visnode.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;

/**
 * Vectorized icon
 */
public class VectorIcon implements Icon {

    /** Font of the icon */
    private final Font font;
    /** Color of the icon */
    private final Color color;
    /** Size of the icon */
    private final int size;
    /** Text that represents the icon */
    private final String text;

    /**
     * Creates a new vector icon
     * 
     * @param font
     * @param color
     * @param size
     * @param text 
     */
    public VectorIcon(Font font, Color color, int size, String text) {
        this.font = font;
        this.color = color;
        this.size = size;
        this.text = text;
    }
    
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font.deriveFont((float)size));
        g2d.setColor(color);
        g2d.drawString(text, x, y + getIconHeight());
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

}
