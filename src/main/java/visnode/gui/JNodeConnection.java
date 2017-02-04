package visnode.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * Connection between nodes
 */
public class JNodeConnection extends JComponent {

    /** First connected node */
    private final JConnectorPoint first;
    /** Second connected node */
    private final JConnectorPoint second;

    /**
     * Creates a new connection between two nodes
     *
     * @param first
     * @param second
     */
    public JNodeConnection(JConnectorPoint first, JConnectorPoint second) {
        super();
        this.first = first;
        this.second = second;

        first.getParentNode().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                update();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                update();
            }

        });

        second.getParentNode().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                update();
            }
            @Override
            public void componentShown(ComponentEvent e) {
                update();
            }

        });
        first.getParentNode().addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                update();
            }
        });

        second.getParentNode().addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                update();
            }
        });
        update();
    }

    private void update() {
        int fx = first.getParentNode().getX() + first.getX();
        int fy = first.getParentNode().getY() + first.getY();
        int sx = second.getParentNode().getX() + second.getX();
        int sy = second.getParentNode().getY() + second.getY();
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
        
        int fx = first.getParentNode().getX() + first.getX();
        int fy = first.getParentNode().getY() + first.getY();
        int sx = second.getParentNode().getX() + second.getX();
        int sy = second.getParentNode().getY() + second.getY();
        
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

}
