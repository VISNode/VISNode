package visnode.pdi.process;

import org.paim.commons.Color;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.commons.Point;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Flood Fill Process
 */
public class FloodFillProcess implements visnode.pdi.Process {

    /** FloodFillProcess process */
    private final org.paim.pdi.FloodFillProcess process;
    
    /**
     * Creates a new FloodFillProcess process
     * 
     * @param image 
     * @param seed 
     * @param replacement 
     */
    public FloodFillProcess(@Input("image") Image image, @Input("seed") Point seed, @Input("color") Color replacement) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.FloodFillProcess(new Image(resultImage), seed, replacement);
        
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
