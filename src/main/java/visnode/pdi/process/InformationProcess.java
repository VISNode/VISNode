package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for getting image informations
 */
public class InformationProcess implements Process {

    /** Information process */
    private final org.paim.pdi.InformationProcess process;

    /**
     * Creates a new information process
     *
     * @param image
     */
    public InformationProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.InformationProcess(resultImage);
    }

    @Override
    public void process() {
        this.process.process();
    }

    /**
     * Returns the average
     *
     * @return int
     */
    @Output("average")
    public int getAverage() {
        return process.getAverage();
    }

}
