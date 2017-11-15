package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import visnode.application.parser.NodeNetworkParser;
import visnode.commons.swing.FileChooserFactory;
import visnode.gui.IconFactory;

/**
 * Action for saving the current project
 */
public class ActionSave extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionSave() {
        super();
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        putValue(SMALL_ICON, IconFactory.get().create("fa:floppy-o"));
        Messages.get().message("save").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (VISNode.get().getModel().getLinkedFile() != null) {
            VISNode.get().getController().save();
        } else {
            FileChooserFactory.saveProject().accept((file) -> {
                VISNode.get().getController().saveAs(file);
            });
        }
    }

}
