package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Vertical mirroring image process
 */
public class VerticalMirroringProcess implements visnode.pdi.Process {

    /** Vertical mirroring process */
    private final org.paim.pdi.VerticalMirroringProcess process;
    
    /**
     * Creates a new horizontal mirroring process
     * 
     * @param image 
     */
    public VerticalMirroringProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.VerticalMirroringProcess(new Image(resultImage));
        
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
