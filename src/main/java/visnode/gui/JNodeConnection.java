package visnode.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JComponent;

/**
 * Connection between nodes
 */
public class JNodeConnection extends JComponent {

    /** Connector line width */
    private static final int LINE_WIDTH = 4;
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

    /**
     * Updates the position of the connection
     */
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
        w = w < LINE_WIDTH ? LINE_WIDTH : w;
        int h = sy - fy;
        h = h < LINE_WIDTH ? LINE_WIDTH : h;
        setBounds(fx, fy, w, h);
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        y1 += LINE_WIDTH / 2;
        y2 -= LINE_WIDTH / 2;
        
        
        g2d.setColor(UIHelper.getColor("NodeConnection.border"));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x1, y1, x2, y2);
        g2d.setPaint(new GradientPaint(new Point(x1, y1), UIHelper.getColor("NodeConnection.color1"), new Point(x2, y2), UIHelper.getColor("NodeConnection.color2")));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y1, x2, y2);
        g2d.dispose();
    }

}
