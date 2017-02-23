
package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import visnode.gui.IconFactory;

/**
 * Action for opening a new project
 */
public class ActionOpen extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionOpen() {
        super("Open", IconFactory.get().create("fa:folder-open"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

}
