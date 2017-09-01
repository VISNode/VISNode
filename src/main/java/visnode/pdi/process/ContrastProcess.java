package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.commons.Range;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.PixelProcess;

/**
 * Process for adjusting the contrast of the image
 */
public class ContrastProcess extends PixelProcess<Image> {

    /** Output image */
    private final Image outputImage;
    /** Contrast */
    private double contrast;

    /**
     * Creates a new contrast process
     *
     * @param image
     * @param contrast
     */
    public ContrastProcess(@Input("image") Image image, @Input("contrast") Double contrast) {
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
        this.contrast = contrast == null ? 1d : contrast;
        this.outputImage = resultImage;
        setFinisher(() -> {
            setOutput(outputImage);
        });
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        Range<Integer> range = outputImage.getPixelValueRange();
        outputImage.set(channel, x, y, range.limit((int) (value * contrast)));
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
