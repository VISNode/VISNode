package visnode.application;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import visnode.commons.swing.FileChooserFactory;
import visnode.gui.IconFactory;

/**
 * Action for opening a new project
 */
public class ActionOpen extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionOpen() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:folder-open"));
        Messages.get().message("open").subscribe((msg) -> {
            putValue(NAME, msg + "...");
        });
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserFactory.openProject().accept((File file) -> {
            VISNode.get().getController().open(file);
        });
    }

}
