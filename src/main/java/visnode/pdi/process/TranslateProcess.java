package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Translate image process
 */
public class TranslateProcess implements visnode.pdi.Process {

    /** Translate process */
    private final org.paim.pdi.TranslateProcess process;
    
    /**
     * Creates a new translate process
     * 
     * @param image 
     * @param x 
     * @param y 
     */
    public TranslateProcess(@Input("image") Image image, @Input("x") Integer x, @Input("y") Integer y) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        if (x == null) {
            x = 0;
        }
        if (y == null) {
            y = 0;
        }
        this.process = new org.paim.pdi.TranslateProcess(new Image(resultImage), x, y);
        
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
