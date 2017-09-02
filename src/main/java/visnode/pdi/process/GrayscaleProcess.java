package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for converting a image to gray scale
 */
public class GrayscaleProcess implements Process {

    /** Gray scale process */
    private final org.paim.pdi.GrayscaleProcess process;

    /**
     * Creates a new gray scale process
     *
     * @param image
     */
    public GrayscaleProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }       
        this.process = new org.paim.pdi.GrayscaleProcess(resultImage);
    }

    /**
     * Returns the output image
     *
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return process.getOutput();
    }

    @Override
    public void process() {
        this.process.process();
    }
}
