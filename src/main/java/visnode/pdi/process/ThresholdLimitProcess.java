package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for applying a binary threshold on a image.
 * <p>
 * This process will return the lower boundary if the pixel is <b>lower than</b>
 * the threshold, and the higher boundary if the pixel is <b> higher than or
 * equal to</b> the boundary.
 */
public class ThresholdLimitProcess implements Process {

    /** Threshold Limit Process */
    private final org.paim.pdi.ThresholdLimitProcess process;

    /**
     * Creates a new Threshold Limit Process
     * 
     * @param image
     * @param lowerThreshold
     * @param higherThreshold
     * @param lowerReplaceValue
     * @param higherReplaceValue
     * @param centerReplaceValue 
     */
    public ThresholdLimitProcess(@Input("image") Image image, 
            @Input("lowerThreshold") Integer lowerThreshold, 
            @Input("higherThreshold") Integer higherThreshold, 
            @Input("lowerReplaceValue") Integer lowerReplaceValue,
            @Input("higherReplaceValue") Integer higherReplaceValue,
            @Input("centerReplaceValue") Integer centerReplaceValue) {
        if (lowerThreshold == null) {
            lowerThreshold = 0;
        }
        if (higherThreshold == null) {
            higherThreshold = 0;
        }
        if (lowerReplaceValue == null) {
            lowerReplaceValue = 0;
        }
        if (higherReplaceValue == null) {
            higherReplaceValue = 0;
        }
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }        
        this.process = new org.paim.pdi.ThresholdLimitProcess(new Image(resultImage), lowerThreshold, higherThreshold, lowerReplaceValue, higherReplaceValue, centerReplaceValue);
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
