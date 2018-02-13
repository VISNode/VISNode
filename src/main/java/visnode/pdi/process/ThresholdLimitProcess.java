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
            @Input("lowerThreshold") Threshold lowerThreshold, 
            @Input("higherThreshold") Threshold higherThreshold, 
            @Input("lowerReplaceValue") Threshold lowerReplaceValue,
            @Input("higherReplaceValue") Threshold higherReplaceValue,
            @Input("centerReplaceValue") Threshold centerReplaceValue) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }        
        this.process = new org.paim.pdi.ThresholdLimitProcess(new Image(resultImage), 
                lowerThreshold.intValue(), 
                higherThreshold.intValue(), 
                lowerReplaceValue.intValue(), 
                higherReplaceValue.intValue(), 
                centerReplaceValue.intValue());
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
