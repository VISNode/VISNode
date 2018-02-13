package visnode.application;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import org.paim.commons.Image;
import org.paim.commons.ImageExporter;
import org.paim.commons.RenderingOptions;
import visnode.commons.DynamicValue;
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
        DynamicValue value = VISNode.get().getModel().getNetwork().getOutput();
        if (!value.isImage()) {
            return;
        }
        FileChooserFactory.exportImage().accept((file) -> {
            try {
                RenderingOptions options = VISNode.get().getModel().getUserPreferences().getRenderingOptions();
                ImageExporter.exportBufferedImage((Image) value.get(), file, options);
            } catch (IOException ex) {
                throw new InvalidOpenFileException(ex);
            }
        });
    }
}
