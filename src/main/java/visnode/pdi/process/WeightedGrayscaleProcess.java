package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for Weighted gray scale 
 */
public class WeightedGrayscaleProcess implements Process {
    
    /** Weighted gray scale */
    private final org.paim.pdi.WeightedGrayscaleProcess process;

    /**
     * Creates a new Weighted gray scale process
     *
     * @param image
     * @param redWeight
     * @param greenWeight
     * @param blueWeight
     */
    public WeightedGrayscaleProcess(@Input("image") Image image, @Input("redWeight") Double redWeight, @Input("greenWeight") Double greenWeight, @Input("blueWeight") Double blueWeight) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        if (redWeight == null) {
            redWeight = 0.3333333333333333d;
        }
        if (greenWeight == null) {
            greenWeight = 0.3333333333333333d;
        }
        if (blueWeight == null) {
            blueWeight = 0.3333333333333333d;
        }
        this.process = new org.paim.pdi.WeightedGrayscaleProcess(new Image(resultImage), redWeight, greenWeight, blueWeight);
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
