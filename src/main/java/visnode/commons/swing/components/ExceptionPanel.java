package visnode.commons.swing.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.apache.commons.lang3.exception.ExceptionUtils;
import visnode.commons.gui.Labels;
import visnode.gui.IconFactory;

/**
 * Exception panel
 */
public class ExceptionPanel extends JPanel {
    
    /** Número de exceptions limite à mostrar no diálogo */
    private final int EXCEPTION_LIMIT = 5;
    /** Exception list */
    private final List<Exception> exceptions;

    /**
     * Creates a new exception panel
     */
    public ExceptionPanel() {
        this(null);
    }

    /**
     * Creates a new exception panel
     * 
     * @param exception 
     */
    public ExceptionPanel(Exception exception) {
        super();
        this.exceptions = new ArrayList<>();
        if (exception != null) {
            exceptions.add(exception);
        }
        initGui();
    }
    
    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        update();
    }
    
    /**
     * Updates the interface
     */
    private void update() {
        SwingUtilities.invokeLater(() -> {
            removeAll();
            if (exceptions.size() == 1) {
                add(buildExceptionPane(exceptions.get(0)));
            } else {
                add(buildTabs());
            }
            repackWindow();
        });
    }
    
    /**
     * Repacks the window
     */
    private void repackWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.pack();
            window.setLocationRelativeTo(null);
        }
    }
    
    /**
     * Build tabs for the exceptions
     * 
     * @return JComponent
     */
    private JComponent buildTabs() {
        JTabbedPane tabs = new JTabbedPane();
        for (Exception exception : exceptions) {
            if (tabs.getTabCount() > EXCEPTION_LIMIT) {
                tabs.add("And " + (exceptions.size() - EXCEPTION_LIMIT) + " more", Labels.create().icon(IconFactory.get().create("fa:frown-o")));
                break;
            }
            tabs.add(exception.getClass().getSimpleName(), buildExceptionPane(exception));
        }
        return tabs;
    }
    
    /**
     * Returns the pane for a single exception
     * 
     * @param exception
     * @return JComponent
     */
    private JComponent buildExceptionPane(Exception exception) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(buildHeader(exception), BorderLayout.NORTH);
        panel.add(buildStack(exception));
        return panel;
    }
    
    /**
     * Builds the header
     * 
     * @param exception
     * @return JComponent
     */
    private JComponent buildHeader(Exception exception) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(Labels.create().icon(IconFactory.get().create("fa:exclamation-triangle")), BorderLayout.WEST);
        panel.add(buildMessageLabel(exception));
        return panel;
    }

    /**
     * Builds the message label
     * 
     * @param exception
     * @return JComponent
     */
    private JComponent buildMessageLabel(Exception exception) {
        JTextArea textArea = new JTextArea(2, 100);
        textArea.setText(exception.getMessage());
        textArea.setBorder(null);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        return textArea;
    }
    
    /**
     * Builds the stack
     * 
     * @param exception
     * @return JComponent
     */
    private JComponent buildStack(Exception exception) {
        JTextArea textArea = new JTextArea(2, 100);
        textArea.setText(ExceptionUtils.getStackTrace(exception));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(0, 600));
        return scrollPane;
    }
    
    /**
     * Adds an exception
     * 
     * @param e 
     */
    public void add(Exception e) {
        exceptions.add(e);
        update();
    }
    
    /**
     * Sets the current exception
     * 
     * @param e 
     */
    public void set(Exception e) {
        exceptions.clear();
        add(e);
    }
    
}
