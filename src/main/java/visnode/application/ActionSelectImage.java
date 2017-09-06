package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import visnode.gui.FileFilterFactory;
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
        super("From image...", IconFactory.get().create("fa:picture-o"));
        this.inputReader = new InputReader();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileFilterFactory.inputFileFilter().apply(chooser);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                if (!chooser.getFileFilter().accept(chooser.getSelectedFile())) {
                    throw new InvalidOpenFileException();
                }
                WebCamCapture.get().stop();
                VISNode.get().getModel().getNetwork().setInput(inputReader.read(chooser.getSelectedFile()));
            } catch (InvalidOpenFileException ex) {
                ExceptionHandler.get().handle(ex);
            } catch (Exception ex) {
                ExceptionHandler.get().handle(ex);
            }
        }
    }

}
