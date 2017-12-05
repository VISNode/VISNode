package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.pdi.ObjectList;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for binary label
 */
public class BinaryLabelingProcess implements Process {

    /** binary label process */
    private final org.paim.pdi.BinaryLabelingProcess process;

    /**
     * Creates a new invert colors process
     *
     * @param image
     */
    public BinaryLabelingProcess(@Input("image") Image image) {
        Image prcImage = image;
        if (image == null) {
            prcImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.BinaryLabelingProcess(new Image(prcImage));
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
    @Output("objectList")
    public ObjectList getImage() {
        return process.getExtractedObjects();
    }

}
