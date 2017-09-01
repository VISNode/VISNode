package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.commons.Threshold;
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
    private final Threshold threshold;
    /** Gray scale image */
    private final Image grayImage;


    /**
     * Creates a new threshold process
     * 
     * @param image
     * @param threshold 
     */
    public ThresholdProcess(@Input("image") Image image, @Input("threshold") Threshold threshold) {
        super(image);
        Threshold resultThreshold;
        if (threshold == null) {
            resultThreshold = new Threshold(0);
        } else {
            resultThreshold = threshold;
        }
        this.threshold = resultThreshold;
        Image resultImage;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        } else {
            resultImage = ImageFactory.
                buildEmptyImage(Image.CHANNELS_GRAYSCALE,
                        image.getWidth(),
                        image.getHeight(),
                        image.getPixelValueRange());
        }
        this.grayImage = resultImage;
        setFinisher(() -> {
            setOutput(grayImage);
        });
    }
    

    @Override
    protected void process(int channel, int x, int y, int value) {
        grayImage.set(channel, x, y, applyThreshold(value));
    }

    /**
     * Apply the threshold
     * 
     * @param value
     * @return 
     */
    private int applyThreshold(int value) {
        if (value < threshold.intValue()) {
            return image.getPixelValueRange().getLower();
        } else {
            return image.getPixelValueRange().getHigher();
        }
    }

    /**    
     * Returns the output image
     * 
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return super.getOutput();
    }

}
