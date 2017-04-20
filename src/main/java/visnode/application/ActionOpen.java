
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
        for (int i = 0; i < 10; i++) {
            doThings();
        }
    }

    private void doThings() {
        System.out.println("I'm a thing!");
    }

}
