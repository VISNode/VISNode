package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Process for channel splitting
 */
public class SplitChannelProcess implements visnode.pdi.Process {

    /** Laplace process */
    private final org.paim.pdi.SplitChannelProcess process;
    
    /**
     * Creates a new channel split process
     * 
     * @param image 
     */
    public SplitChannelProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.SplitChannelProcess(new Image(resultImage));
        
    }

    @Override
    public void process() {
        process.process();
    }
    
    /**
     * Returns the red output image
     *
     * @return Image
     */
    @Output("imageRed")
    public Image getRedImage() {
        return process.getOutput()[Image.CHANNEL_RED];
    }
    
    /**
     * Returns the green output image
     *
     * @return Image
     */
    @Output("imageGreen")
    public Image getGreenImage() {
        return process.getOutput()[Image.CHANNEL_GREEN];
    }
    
    /**
     * Returns the blue output image
     *
     * @return Image
     */
    @Output("imageBlue")
    public Image getBlueImage() {
        return process.getOutput()[Image.CHANNEL_BLUE];
    }

}
