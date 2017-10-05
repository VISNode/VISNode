package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Marr and Hildreth process for edge detection
 */
public class MarrHildrethProcess implements Process {

    /** Marr and Hildreth process */
    private final org.paim.pdi.MarrHildrethProcess process;
    
    /**
     * Creates a new Marr and Hildreth process
     * 
     * @param image 
     */
    public MarrHildrethProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.MarrHildrethProcess(new Image(resultImage));
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
