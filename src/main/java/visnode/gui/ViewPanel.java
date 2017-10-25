
package visnode.gui;

import java.awt.BorderLayout;
import javax.swing.JComponent;

/**
 * Panel for views
 */
public class ViewPanel extends JComponent {

    /** View */
    private final JComponent view;
    
    /**
     * Creates the panel
     * 
     * @param view 
     */
    public ViewPanel(JComponent view) {
        super();
        this.view = view;
        initGui();
    }
    
    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(view);
    }
    
}
