package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * List item component
 */
public class ListItemComponent extends JPanel {

    /** Border */
    private JPanel componentBorder;

    public ListItemComponent() {
        super();
        initGui();
    }

    /**
     * Initialize the interface
     */
    private void initGui() {
        componentBorder = new JPanel();
        componentBorder.setLayout(new BorderLayout());
        componentBorder.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        super.add(componentBorder);
    }

    @Override
    public Component add(Component comp) {
        componentBorder.add(comp);
        return this;
    }

    @Override
    public void add(Component comp, Object constraints) {
        componentBorder.add(comp, constraints);
    }

}
