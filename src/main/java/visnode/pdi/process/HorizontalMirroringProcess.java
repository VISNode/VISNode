package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Horizontal mirroring image process
 */
public class HorizontalMirroringProcess implements visnode.pdi.Process {

    /** Horizontal mirroring process */
    private final org.paim.pdi.HorizontalMirroringProcess process;
    
    /**
     * Creates a new horizontal mirroring process
     * 
     * @param image 
     */
    public HorizontalMirroringProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.HorizontalMirroringProcess(new Image(resultImage));
        
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
