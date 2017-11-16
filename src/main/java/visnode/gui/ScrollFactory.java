package visnode.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * Scroll factory
 */
public class ScrollFactory {
    
    /**
     * Creates a new Pane
     * 
     * @param view
     * @return ScrollPaneBuilder
     */
    public static ScrollPaneBuilder pane(JComponent view) {
        return new ScrollPaneBuilder(view).allowMidScroll();
    }
    
    /**
     * Scroll pane builder
     */
    public static class ScrollPaneBuilder {
        
        /** Scroll pane */
        private final JScrollPane scrollPane;

        /**
         * Creates a new builder
         * 
         * @param view 
         */
        public ScrollPaneBuilder(JComponent view) {
            this.scrollPane = new JScrollPane(view);
        }
        
        /**
         * Allows scroll using middle-mouse button
         * 
         * @return ScrollPaneBuilder
         */
        public ScrollPaneBuilder allowMidScroll() {
            MidScrollSupport support = new MidScrollSupport(scrollPane);
            scrollPane.getViewport().getView().addMouseListener(support);
            scrollPane.getViewport().getView().addMouseMotionListener(support);
            return this;
        }
        
        /**
         * Creates the scroll pane
         * 
         * @return JScrollPane
         */
        public JScrollPane create() {
            return scrollPane;
        }
        
    }
    
    /**
     * Middle-mouse scroll support
     */
    private static class MidScrollSupport implements MouseListener, MouseMotionListener {

        /** Scroll pane */
        private final JScrollPane scrollPane;
        /** Last position */
        private Point lastPos;

        /**
         * Crates a new middle-mouse scroll support
         * 
         * @param scrollPane 
         */
        public MidScrollSupport(JScrollPane scrollPane) {
            this.scrollPane = scrollPane;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON2) {
                lastPos = e.getLocationOnScreen();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            lastPos = null;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (lastPos != null) {
                int xDiff = lastPos.x - e.getLocationOnScreen().x;
                int yDiff = lastPos.y - e.getLocationOnScreen().y;
                 scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + xDiff);
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() + yDiff);
                lastPos = new Point(e.getLocationOnScreen());
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
        
    }
    
}
