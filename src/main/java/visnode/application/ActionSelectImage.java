package visnode.application;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import org.paim.commons.ImageFactory;
import visnode.gui.IconFactory;

/**
 * Action to select image for input
 */
public class ActionSelectImage extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionSelectImage() {
        super("From image...", IconFactory.get().create("fa:picture-o"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                WebCamCapture.get().stop();
                BufferedImage r = ImageIO.read(chooser.getSelectedFile());
                VISNode.get().getModel().getNetwork().setInput(ImageFactory.buildRGBImage(r));
            } catch (Exception ex) {
                ExceptionHandler.get().handle(ex);
            }
        }
    }

}
