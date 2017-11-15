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
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:clipboard"));
        Messages.get().message("paste").subscribe((msg) -> {
            putValue(NAME, msg);
        });
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        VISNode.get().getController().pasteNodes();
    }

}
