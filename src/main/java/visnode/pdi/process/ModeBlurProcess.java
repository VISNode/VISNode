package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Process for Mode blur
 */
public class ModeBlurProcess implements visnode.pdi.Process {

    /** Mode blur process */
    private final org.paim.pdi.ModeBlurProcess process;
    
    /**
     * Creates a new Mode blur process
     * 
     * @param image 
     */
    public ModeBlurProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.ModeBlurProcess(new Image(resultImage));
        
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
