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
    /** Input reader */
    private final InputReader inputReader;
    
    /**
     * Creates a new action
     *
     * @param file
     */
    public ActionDirectOpenImage(File file) {
        super(file.getAbsolutePath());
        this.file = file;
        this.inputReader = new InputReader();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            WebCamCapture.get().stop();
            VISNode.get().getModel().getNetwork().setInput(inputReader.read(file));
        } catch (IOException ex) {
            throw new InvalidOpenFileException(ex);
        }
    }

}
