package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import visnode.commons.swing.FileChooserFactory;
import visnode.gui.IconFactory;

/**
 * Action for saving the current project
 */
public class ActionSaveAs extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionSaveAs() {
        putValue(SMALL_ICON, IconFactory.get().create("fa:floppy-o"));
        Messages.get().message("saveAs").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserFactory.saveProject().accept((file) -> {
            VISNode.get().getController().saveAs(file);
        });
    }

}
