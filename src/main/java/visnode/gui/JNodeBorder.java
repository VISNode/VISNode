package visnode.gui;

import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.border.Border;

/**
 * Node border
 */
public class JNodeBorder implements Border {

    /** Header height */
    private static final int HEADER_HEIGHT = 30;
    /** Node */
    private final JNode node;
    /** Title */
    private final String title;
    
    /**
     * Creates a new node border
     * 
     * @param node 
     */
    public JNodeBorder(JNode node) {
        this(node, "");
    }
    
    /**
     * Creates a new node border
     * 
     * @param node 
     * @param title
     */
    public JNodeBorder(JNode node, String title) {
        this.node = node;
        this.title = title;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(UIHelper.getColor("Node.background"));
        g2d.fillRoundRect(x + 5, y + 5, width - 11, height - 11, 10, 10);
        paintHeader(g2d, x, y, width, height);
        if (node.getParentNodeContainer().getSelection().contains(node)) {
            g2d.setColor(UIHelper.getColor("Node.border:selected"));
        } else {
            g2d.setColor(UIHelper.getColor("Node.border"));
        }
        g2d.drawRoundRect(x + 5, y + 5, width - 11, height - 11, 10, 10);
        g2d.setColor(UIHelper.getColor("Node.Header.title"));
        g2d.drawString(title, 19, 22);
        g2d.dispose();
    }

    /**
     * Paints the header
     * 
     * @param g
     */
    private void paintHeader(Graphics2D g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setClip(new Rectangle(x, y, width, HEADER_HEIGHT));
        g2d.setPaint(new GradientPaint(new Point(0, 0), UIHelper.getColor("Node.Header.background1"), new Point(0, HEADER_HEIGHT), UIHelper.getColor("Node.Header.background2")));
        g2d.fillRoundRect(x + 5, y + 5, width - 11, height - 11, 10, 10);
        g2d.dispose();
    }
    
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(HEADER_HEIGHT, 0, 10, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

}
