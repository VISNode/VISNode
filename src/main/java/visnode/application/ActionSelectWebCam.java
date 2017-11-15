
package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.paim.commons.ImageFactory;
import visnode.gui.IconFactory;

/**
 * Action to select image for webCam
 */
public class ActionSelectWebCam extends AbstractAction {
    
    /**
     * Creates a new action
     */
    public ActionSelectWebCam() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:video-camera"));
        Messages.get().message("fromWebcam").subscribe((msg) -> {
            putValue(NAME, msg + "...");
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WebCamCapture.get().capture((image) -> {
            VISNode.get().getModel().getNetwork().setInput(ImageFactory.buildRGBImage(image));
        });
    }
    
}
