package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for Weighted grayscale 
 */
public class WeightedGrayscaleProcess implements Process {
    
    /** Weighted grayscale */
    private final org.paim.pdi.WeightedGrayscaleProcess process;

    /**
     * Creates a new Weighted grayscale process
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
