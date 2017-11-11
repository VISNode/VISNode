package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.KeyStroke;
import visnode.gui.IconFactory;

/**
 * Action for copying nodes
 */
public class ActionCopyNode extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionCopyNode() {
        super(Messages.get().message("copy"), IconFactory.get().create("fa:files-o"));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl C"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        VISNode.get().getController().copyNodes();
    }

}