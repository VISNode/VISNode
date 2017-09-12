package visnode.commons.swing;

import java.awt.BorderLayout;
import java.awt.Window;
import java.util.function.Consumer;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * Frame and dialog factory
 */
public class WindowFactory {
    
    /**
     * Creates a model dialog
     * 
     * @return DialogBuilder
     */
    public static DialogBuilder modal() {
        return new DialogBuilder();
    }
    
    /**
     * Creates a model dialog
     * 
     * @return FrameBuilder
     */
    public static FrameBuilder mainFrame() {
        return new FrameBuilder();
    }
    
    /**
     * Dialog builder
     */
    public static class DialogBuilder extends WindowBuilder<JDialog, DialogBuilder> {

        /**
         * Dialog builder
         */
        public DialogBuilder() {
            super(new JDialog());
        }
        
        @Override
        public DialogBuilder title(String title) {
            window.setTitle(title);
            return this;
        }

        @Override
        public DialogBuilder menu(JMenuBar menu) {
            window.setJMenuBar(menu);
            return this;
        }
        
        @Override
        public JDialog create(Consumer<JPanel> consumer) {
            window.setIconImage(new ImageIcon(getClass().getResource("/VISNode_64.png").getFile()).getImage());
            window.setModal(true);
            JPanel container = new JPanel(new BorderLayout());
            consumer.accept(container);
            window.getContentPane().setLayout(new BorderLayout());
            window.getContentPane().add(container);
            window.pack();
            window.setLocationRelativeTo(null);
            return window;
        }
        
    }
    
    /**
     * Frame builder
     */
    public static class FrameBuilder extends WindowBuilder<JFrame, FrameBuilder> {

        /**
         * Frame builder
         */
        public FrameBuilder() {
            super(new JFrame());
        }
        
        @Override
        public FrameBuilder title(String title) {
            window.setTitle(title);
            return this;
        }

        @Override
        public FrameBuilder menu(JMenuBar menu) {
            window.setJMenuBar(menu);
            return this;
        }
        
        /**
         * Maximized
         * 
         * @return FrameBuilder
         */
        public FrameBuilder maximized() {
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            return this;
        }
        
        @Override
        public JFrame create(Consumer<JPanel> consumer) {
            window.setIconImage(new ImageIcon(getClass().getResource("/VISNode_64.png").getFile()).getImage());
            JPanel container = new JPanel(new BorderLayout());
            consumer.accept(container);
            window.getContentPane().setLayout(new BorderLayout());
            window.getContentPane().add(container);
            window.pack();
            window.setLocationRelativeTo(null);
            return window;
        }
        
    }
    
    /**
     * Abstract window builder
     * @param <T> 
     */
    private static abstract class WindowBuilder<T extends Window, B extends WindowBuilder> {
    
        /** Window */
        protected final T window;
        
        /**
         * Window builder
         * 
         * @param window
         */
        public WindowBuilder(T window) {
            this.window = window;
        }
        
        /**
         * Sets the size
         * 
         * @param width
         * @param height
         * @return B
         */
        public B size(int width, int height) {
            window.setSize(width, height);
            return (B) this;
        }
        
        /**
         * Sets the title
         * 
         * @param title
         * @return B
         */
        public abstract B title(String title);
        
        /**
         * Sets the menu
         * 
         * @param menu
         * @return B
         */
        public abstract B menu(JMenuBar menu);
        
        /**
         * Creates the window
         * 
         * @param consumer
         * @return T
         */
        public abstract T create(Consumer<JPanel> consumer);
        
    }
    
}
