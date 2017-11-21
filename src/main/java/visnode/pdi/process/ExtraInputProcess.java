package visnode.pdi.process;

import java.io.IOException;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.application.ExceptionHandler;
import visnode.application.InputReader;
import visnode.commons.Input;
import visnode.commons.MultiFile;
import visnode.commons.Output;

/**
 *
 */
public class ExtraInputProcess implements visnode.pdi.Process {
    
    /** Extra image */
    private Image image;

    public ExtraInputProcess(@Input("file") MultiFile file) {
        if (file == null) {
            this.image = ImageFactory.buildEmptyImage();
        } else {
            try {
                this.image = new InputReader().read(file.getFile());
            } catch (IOException e) {
                ExceptionHandler.get().handle(e);
            }
        }
    }    
    
    @Override
    public void process() {
    }
    
    /**
     * Returns the image
     *
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return image;
    }
    
}
