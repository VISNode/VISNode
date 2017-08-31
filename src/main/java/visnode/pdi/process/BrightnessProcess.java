package visnode.pdi.process;

import org.torax.commons.Image;
import org.torax.commons.ImageFactory;
import org.torax.commons.Range;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.PixelProcess;

/**
 * Process for adjusting the brightness of the image
 */
public class BrightnessProcess extends PixelProcess<Image> {

    /** Output image */
    private final Image outputImage;
    /** Brightness */
    private int brightness;

    /**
     * Creates a new brightness process
     *
     * @param image
     * @param brightness
     */
    public BrightnessProcess(@Input("image") Image image, @Input("brightness") Integer brightness) {
        super(image);
        Image resultImage;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        } else {
            resultImage = ImageFactory.
                buildEmptyImage(image.getChannelCount(),
                        image.getWidth(),
                        image.getHeight(),
                        image.getPixelValueRange());
        }
        this.brightness = brightness == null ? 0 : brightness;
        this.outputImage = resultImage;
        setFinisher(() -> {
            setOutput(outputImage);
        });
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        Range<Integer> range = outputImage.getPixelValueRange();
        outputImage.set(channel, x, y, range.limit((int) (value + brightness)));
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
