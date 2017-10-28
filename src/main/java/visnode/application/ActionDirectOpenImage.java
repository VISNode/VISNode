package visnode.application;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;

/**
 * Action for opening a project directly
 */
public class ActionDirectOpenImage extends AbstractAction {

    /** File to open */
    private final File file;

    /**
     * Creates a new action
     *
     * @param file
     */
    public ActionDirectOpenImage(File file) {
        super(file.getAbsolutePath());
        this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WebCamCapture.get().stop();
        VISNode.get().getModel().getNetwork().setInput(file);
    }

}
