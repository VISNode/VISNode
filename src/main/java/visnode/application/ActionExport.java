package visnode.application;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import org.paim.commons.Image;
import org.paim.commons.ImageExporter;
import org.paim.commons.RenderingOptions;
import visnode.commons.swing.FileChooserFactory;

/**
 * Action for export the output
 */
public class ActionExport extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionExport() {
        super();
        Messages.get().message("export").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserFactory.exportImage().accept((file) -> {
            try {
                RenderingOptions options = VISNode.get().getModel().getUserPreferences().getRenderingOptions();
                Image image = VISNode.get().getModel().getNetwork().getOutput();
                ImageExporter.exportBufferedImage(image, file, options);
            } catch (IOException ex) {
                throw new InvalidOpenFileException(ex);
            }
        });
    }
}
