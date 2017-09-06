package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for adjusting the brightness of the image
 */
public class BrightnessProcess implements Process {
    
    /** Brightness process*/
    private final org.paim.pdi.BrightnessProcess process;

    /**
     * Creates a new brightness process
     *
     * @param image
     * @param brightness
     */
    public BrightnessProcess(@Input("image") Image image, @Input("brightness") Integer brightness) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.BrightnessProcess(new Image(resultImage), brightness);
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
