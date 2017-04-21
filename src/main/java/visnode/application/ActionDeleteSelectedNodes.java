
package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import visnode.gui.IconFactory;

/**
 * Action - Delete selected nodes
 */
public class ActionDeleteSelectedNodes extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionDeleteSelectedNodes() {
        super("Delete seletected nodes", IconFactory.get().create("fa:trash"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

}
