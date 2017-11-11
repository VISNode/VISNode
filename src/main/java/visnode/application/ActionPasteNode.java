package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.KeyStroke;
import visnode.gui.IconFactory;

/**
 * Action for pasting nodes
 */
public class ActionPasteNode extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionPasteNode() {
        super(Messages.get().message("paste"), IconFactory.get().create("fa:clipboard"));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        VISNode.get().getController().pasteNodes();
    }
    
}
