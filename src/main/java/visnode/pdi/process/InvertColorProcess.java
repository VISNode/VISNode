package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for inverting the image colors
 */
public class InvertColorProcess implements Process {

    /** Invert color process */
    private final org.paim.pdi.InvertColorProcess process;

    /**
     * Creates a new invert colors process
     *
     * @param image
     */
    public InvertColorProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.InvertColorProcess(new Image(resultImage));
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
