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
    
    /** First connected node */
    private final PositionSupplier first;
    /** Second connected node */
    private final PositionSupplier second;
    /** Points of the connection */
    private Point[] points;

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
        Point f0 = new Point(first.getPosition());
        Point f3 = new Point(second.getPosition());
        float dist = (float) f0.distance(f3);
        float strenght = Math.min(dist, 100);
        Point f1 = new Point(f0);
        f1.x += strenght;
        Point f2 = new Point(f3);
        f2.x -= strenght;
        int segments = (int) (dist / 3);
        points = new Point[segments + 2];
        points[0] = f0;
        for (int i = 0; i < segments; i++) {
            float t = i / (float) segments;
            points[i + 1] = calculateBezierPoint(t, f0, f1, f2, f3);
        }
        points[segments + 1] = f3;
        int minx = Integer.MAX_VALUE;
        int miny = Integer.MAX_VALUE;
        int maxx = Integer.MIN_VALUE;
        int maxy = Integer.MIN_VALUE;
        for (Point point : points) {
            minx = Math.min(point.x, minx);
            maxx = Math.max(point.x, maxx);
            miny = Math.min(point.y, miny);
            maxy = Math.max(point.y, maxy);
        }
        setBounds(minx, miny, maxx - minx, maxy - miny);
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
        g2d.translate(-getX(), -getY());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLUE);
        int[] xs = new int[points.length];
        int[] ys = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xs[i] = points[i].x;
            ys[i] = points[i].y;
        }
        g2d.setColor(UIHelper.getColor("NodeConnection.border"));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawPolyline(xs, ys, points.length);
        g2d.setPaint(new GradientPaint(points[0], UIHelper.getColor("NodeConnection.color1"), points[points.length - 1], UIHelper.getColor("NodeConnection.color2")));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawPolyline(xs, ys, points.length);
        g2d.dispose();
    }

    private Point calculateBezierPoint(float t, Point p0, Point p1, Point p2, Point p3) {
        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;
        float x = uuu * p0.x; //first term
        x += 3 * uu * t * p1.x; //second term
        x += 3 * u * tt * p2.x; //third term
        x += ttt * p3.x; 
        float y = uuu * p0.y; //first term
        y += 3 * uu * t * p1.y; //second term
        y += 3 * u * tt * p2.y; //third term
        y += ttt * p3.y; 
        return new Point((int)x, (int)y);
    }

}
