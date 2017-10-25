package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Edge detection using the Gradient process
 */
public class GradientProcess implements visnode.pdi.Process {

    /** Gradient process */
    private final org.paim.pdi.GradientProcess process;

    /**
     * Creates a new Gradient process
     *
     * @param image
     */
    public GradientProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.GradientProcess(new Image(resultImage));

    }

    @Override
    public void process() {
        process.process();
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

}
