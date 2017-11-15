
package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import visnode.gui.IconFactory;

/**
 *
 * @author Nícolas Pohren
 */
public class ActionNew extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionNew() {
        super();
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(SMALL_ICON, IconFactory.get().create("fa:file"));
        Messages.get().message("new").subscribe((msg) -> {
            putValue(NAME, msg);
        });        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        VISNode.get().getController().createNew();
    }

}
