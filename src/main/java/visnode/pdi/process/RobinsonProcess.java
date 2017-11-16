package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Edge detection using the Robinson process
 */
public class RobinsonProcess implements visnode.pdi.Process {

    /** Roberts process */
    private final org.paim.pdi.RobinsonProcess process;

    /**
     * Creates a new Robinson process
     *
     * @param image
     */
    public RobinsonProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.RobinsonProcess(new Image(resultImage));

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
