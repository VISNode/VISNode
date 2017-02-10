package visnode.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

/**
 * Node
 */
public class JNode extends JComponent {

    /**
     * Node
     */
    public JNode() {
        super();
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setBorder(BorderFactory.createEtchedBorder());
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
