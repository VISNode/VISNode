package visnode.application;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.KeyStroke;
import visnode.commons.swing.FileChooserFactory;
import visnode.gui.IconFactory;

/**
 * Action to select image for input
 */
public class ActionSelectImage extends AbstractAction {

    /** Input reader */
    private final InputReader inputReader;
    
    /**
     * Creates a new action
     */
    public ActionSelectImage() {
        super(Messages.get().message("fromImage") + "...", IconFactory.get().create("fa:picture-o"));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift I"));
        this.inputReader = new InputReader();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserFactory.openImage().accept((file) -> {
            try {
                WebCamCapture.get().stop();
                VISNode.get().getModel().getNetwork().setInput(inputReader.read(file));
            } catch (IOException ex) {
                throw new InvalidOpenFileException(ex);
            }
        });
    }

}
