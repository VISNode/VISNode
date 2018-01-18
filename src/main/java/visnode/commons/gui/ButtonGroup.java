package visnode.commons.gui;

import java.awt.GridLayout;
import javax.swing.AbstractButton;

/**
 * Button group
 */
public class ButtonGroup extends Panel {

    /** Group of buttons */
    private final javax.swing.ButtonGroup group;

    /**
     * Creates a new button group
     */
    public ButtonGroup() {
        super();
        group = new javax.swing.ButtonGroup();
        setLayout(new GridLayout(1, 0, 0, 0));
    }
    
    @Override
    public void put(Component component) {
        if (!(component instanceof GenericButton) || !(component instanceof AbstractButton)) {
            throw new IllegalArgumentException("Only buttons for ButtonGroup!");
        }
        group.add((AbstractButton) component);
        super.put(component);
    }
    
}
