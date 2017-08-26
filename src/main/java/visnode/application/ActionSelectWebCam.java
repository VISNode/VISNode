
package visnode.application;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import visnode.commons.ImageFactory;
import visnode.gui.IconFactory;

/**
 * Action to select image for webCam
 */
public class ActionSelectWebCam extends AbstractAction {
    
    /**
     * Creates a new action
     */
    public ActionSelectWebCam() {
        super("From webcam...", IconFactory.get().create("fa:video-camera"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WebCamCapture.get().capture((image) -> {
            VISNode.get().getModel().getNetwork().setInput(ImageFactory.buildRGBImage(image));
        });
    }
    
}
