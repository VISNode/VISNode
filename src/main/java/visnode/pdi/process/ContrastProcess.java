package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for adjusting the contrast of the image
 */
public class ContrastProcess implements Process {

    /** Contrast process */
    private final org.paim.pdi.ContrastProcess process;
    
    /**
     * Creates a new contrast process
     *
     * @param image
     * @param contrast
     */
    public ContrastProcess(@Input("image") Image image, @Input("contrast") Double contrast) {
       Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        Double resultContrast = contrast == null ? 1d : contrast;
        this.process = new org.paim.pdi.ContrastProcess(new Image(resultImage), resultContrast);
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
