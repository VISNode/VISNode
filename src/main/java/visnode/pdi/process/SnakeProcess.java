package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Snake process
 */
public class SnakeProcess implements visnode.pdi.Process {

    /** Snake process */
    private final org.paim.pdi.SnakeProcess process;
    
    /**
     * Creates a new snake process
     * 
     * @param image 
     */
    public SnakeProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.SnakeProcess(new Image(resultImage), 1000, 1, 1, 1);
        
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
