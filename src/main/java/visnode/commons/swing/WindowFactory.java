package visnode.commons.swing;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * Frame and dialog factory
 */
public class WindowFactory {
    
    /**
     * Creates a modal dialog
     * 
     * @return DialogBuilder
     */
    public static DialogBuilder modal() {
        return new DialogBuilder().modal();
    }

    /**
     * Creates a frame
     * 
     * @return FrameBuilder
     */
    public static FrameBuilder frame() {
        return new FrameBuilder();
    }
    
    /**
     * Creates a main application frame
     * 
     * @return FrameBuilder
     */
    public static FrameBuilder mainFrame() {
        return new FrameBuilder().onClose((e) -> {
            System.exit(0);
        });
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
        public DialogBuilder doNothingOnClose() {
            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            return this;
        }

        /**        
         * Sets a dialog as modal
         * 
         * @return DialogBuilder
         */
        public DialogBuilder modal() {
            window.setModal(true);
            return this;
        }
        
        @Override
        public JDialog create(Consumer<JPanel> consumer) {
            window.setIconImage(new ImageIcon(getClass().getResource("/VISNode_64.png").getFile()).getImage());
            JPanel container = new JPanel(new BorderLayout());
            container.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new CompoundBorder(BorderFactory.createEtchedBorder(), new EmptyBorder(6, 6, 6, 6))));
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
        
        @Override
        public FrameBuilder doNothingOnClose() {
            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
    private static abstract class WindowBuilder<T extends Window, B extends WindowBuilder> implements WindowListener {
    
        /** Window */
        protected final T window;
        /** Incertepct closing callback */
        private Supplier<Boolean> interceptClosing;
        /** On close callback */
        private Consumer<WindowEvent> onClose;
        
        /**
         * Window builder
         * 
         * @param window
         */
        public WindowBuilder(T window) {
            this.window = window;
            this.window.addWindowListener(this);
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
         * Intercept the window closing event
         * 
         * @param callback
         * @return B
         */
        public B interceptClose(Supplier<Boolean> callback) {
            doNothingOnClose();
            interceptClosing = callback;
            return (B) this;
        }
        
        /**
         * Do nothing on close
         * 
         * @return B
         */
        public abstract B doNothingOnClose();
        
        /**
         * On the window close. Happens after the interceptClose
         * 
         * @param callback
         * @return B
         */
        public B onClose(Consumer<WindowEvent> callback) {
            onClose = callback;
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

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            if (interceptClosing.get()) {
                window.dispose();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
            onClose.accept(e);
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
        
    }
    
}
