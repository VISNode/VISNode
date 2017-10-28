package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.KeyStroke;
import visnode.commons.swing.FileChooserFactory;
import visnode.gui.IconFactory;

/**
 * Action to select image for input
 */
public class ActionSelectImage extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionSelectImage() {
        super(Messages.get().message("fromImage") + "...", IconFactory.get().create("fa:picture-o"));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserFactory.openImage().accept((file) -> {
            WebCamCapture.get().stop();
            VISNode.get().getModel().getNetwork().setInput(file);
            VISNode.get().getModel().getUserPreferences().addRecentInput(file);
            VISNode.get().getController().fireRecentInputs();
        });
    }

}
