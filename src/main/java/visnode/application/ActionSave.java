
package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import visnode.gui.IconFactory;

/**
 * Action for saving the current project
 */
public class ActionSave extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionSave() {
        super("Save", IconFactory.get().create("fa:floppy-o"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

}
