package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.commons.Threshold;
import visnode.pdi.Process;

/**
 * Process for applying a binary threshold on a image.
 * <p>
 * This process will return the lower boundary if the pixel is <b>lower than</b>
 * the threshold, and the higher boundary if the pixel is <b> higher than or 
 * equal to</b> the boundary.
 */
public class ThresholdProcess implements Process {

    /** Threshold process */
    private final org.paim.pdi.ThresholdProcess process;

    /**
     * Creates a new threshold process
     * 
     * @param image
     * @param threshold 
     */
    public ThresholdProcess(@Input("image") Image image, @Input("threshold") Threshold threshold) {
        Threshold resultThreshold;
        if (threshold == null) {
            resultThreshold = new Threshold(0);
        } else {
            resultThreshold = threshold;
        }
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }        
        this.process = new org.paim.pdi.ThresholdProcess(new Image(resultImage), resultThreshold.intValue());
    }

    /**    
     * Returns the output image
     * 
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return this.process.getOutput();
    }

    @Override
    public void process() {
        this.process.process();
    }

}
