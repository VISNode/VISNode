package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Gaussian Blur process
 */
public class GaussianBlurProcess implements Process {

    /** Gaussian Blur process */
    private final org.paim.pdi.GaussianBlurProcess process;

    /**
     * Creates a new Gaussian blur process
     *
     * @param image
     * @param sigma
     * @param maskSize
     */
    public GaussianBlurProcess(@Input("image") Image image, @Input("sigma") Double sigma, @Input("maskSize") Integer maskSize) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.GaussianBlurProcess(new Image(resultImage), sigma, maskSize);
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
