
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
        super(Messages.get().message("new"), IconFactory.get().create("fa:file"));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        VISNode.get().getController().createNew();
    }

}
