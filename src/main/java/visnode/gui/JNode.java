package visnode.gui;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

/**
 * Node
 */
public class JNode extends JComponent {

    /** Title */
    private final String title;
    
    /**
     * Node
     */
    public JNode() {
        this("");
    }
    
    /**
     * Node
     * 
     * @param title Title
     */
    public JNode(String title) {
        super();
        this.title = title;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setBorder(new JNodeBorder(this, title));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Returns the parent node container
     * 
     * @return JNodeContainer
     */
    public JNodeContainer getParentNodeContainer() {
        if (getParent() instanceof JNodeContainer) {
            return (JNodeContainer) getParent();
        } else {
            throw new IllegalStateException("JNode must be inside a JNodeContainer!");
        }
    }

}
