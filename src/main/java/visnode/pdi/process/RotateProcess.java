package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Angle;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Rotate image process
 */
public class RotateProcess implements visnode.pdi.Process {

    /** Rotate process */
    private final org.paim.pdi.RotateProcess process;

    /**
     * Creates a new rotate process
     *
     * @param image
     * @param angle
     */
    public RotateProcess(@Input("image") Image image, @Input("angle") Angle angle) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        Angle resultAngle = angle;
        if (angle == null) {
            resultAngle = new Angle(0);
        }
        this.process = new org.paim.pdi.RotateProcess(new Image(resultImage), resultAngle.intValue());

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
