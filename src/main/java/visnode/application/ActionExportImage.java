package visnode.application;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import org.paim.commons.Image;
import org.paim.commons.ImageExporter;
import visnode.commons.swing.FileChooserFactory;
import visnode.gui.IconFactory;

/**
 * Action for exporting an image
 */
public class ActionExportImage extends AbstractAction {

    /** Image to export */
    private final Image image;

    /**
     * Creates a new action
     * 
     * @param image
     */
    public ActionExportImage(Image image) {
        super("Export", IconFactory.get().create("fa:floppy-o"));
        this.image = image;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserFactory.exportImage().accept((file) -> {
            try {
                ImageExporter.exportBufferedImage(image, file);
            } catch (IOException ex) {
                throw new InvalidOpenFileException(ex);
            }
        });
    }

}
