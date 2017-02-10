package visnode.pdi.process;

import visnode.commons.Image;
import visnode.commons.Input;
import visnode.pdi.PixelProcess;

/**
 * Process for applying a binary threshold on a image.
 * <p>
 * This process will return the lower boundary if the pixel is <b>lower than</b>
 * the threshold, and the higher boundary if the pixel is <b> higher than or 
 * equal to</b> the boundary.
 */
public class ThresholdProcess extends PixelProcess<Image> {

    /** Threshold */
    private final int threshold;

    /**
     * Creates a new threshold process
     * 
     * @param image
     * @param threshold 
     */
    public ThresholdProcess(@Input("image") Image image, @Input("threshold") int threshold) {
        super(image);
        this.threshold = threshold;
        setFinisher(() -> {
            setOutput(image);
        });
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        image.set(channel, x, y, applyThreshold(value));
    }

    /**
     * Apply the threshold
     * 
     * @param value
     * @return 
     */
    private int applyThreshold(int value) {
        if (value < threshold) {
            return image.getPixelValueRange().getLower();
        } else {
            return image.getPixelValueRange().getHigher();
        }
    }

}
