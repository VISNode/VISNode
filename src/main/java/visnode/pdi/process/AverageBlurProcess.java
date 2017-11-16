package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Process for Average blur
 */
public class AverageBlurProcess implements visnode.pdi.Process {

    /** Average blur process */
    private final org.paim.pdi.AverageBlurProcess process;
    
    /**
     * Creates a new Average blur process
     * 
     * @param image 
     */
    public AverageBlurProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.AverageBlurProcess(new Image(resultImage));
        
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
