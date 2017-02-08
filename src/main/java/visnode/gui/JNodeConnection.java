package visnode.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * Connection between nodes
 */
public class JNodeConnection extends JComponent {

    /** First connected node */
    private final PositionSupplier first;
    /** Second connected node */
    private final PositionSupplier second;

    /**
     * Creates a new connection between two nodes
     *
     * @param first
     * @param second
     */
    public JNodeConnection(PositionSupplier first, PositionSupplier second) {
        super();
        this.first = first;
        this.second = second;
        first.addPositionListener((newPosition) -> {
            update();
        });
        second.addPositionListener((newPosition) -> {
            update();
        });
        update();
    }

    private void update() {
        if (first == null || second == null || first.getPosition() == null || second.getPosition() == null) {
            return;
        }
        int fx = first.getPosition().x;
        int fy = first.getPosition().y;
        int sx = second.getPosition().x;
        int sy = second.getPosition().y;
        if (fx > sx) {
            int ax = fx;
            fx = sx;
            sx = ax;
        }
        if (fy > sy) {
            int ay = fy;
            fy = sy;
            sy = ay;
        }
        int w = sx - fx;
        w = w < 2 ? 2 : w;
        int h = sy - fy;
        h = h < 2 ? 2 : h;
        setBounds(fx + 5, fy + 5, w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLUE);
        
        int fx = first.getPosition().x;
        int fy = first.getPosition().y;
        int sx = second.getPosition().x;
        int sy = second.getPosition().y;
        
        int x1, x2, y1, y2;
        
        if (fx > sx) {
            x1 = getWidth() - 1;
            x2 = 0;
        } else {
            x1 = 0;
            x2 = getWidth() - 1;
        }
        if (fy > sy) {
            y1 = getHeight() - 1;
            y2 = 0;
        } else {
            y1 = 0;
            y2 = getHeight() - 1;
        }
        g2d.drawLine(x1, y1, x2, y2);
        g2d.dispose();
    }

    /**
     * Return the first position supplier for the connection
     * 
     * @return PositionSupplier
     */
    public PositionSupplier getFirst() {
        return first;
    }

    /**
     * Return the second position supplier for the connection
     * 
     * @return PositionSupplier
     */
    public PositionSupplier getSecond() {
        return second;
    }

}
