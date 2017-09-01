package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.commons.Range;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.PixelProcess;

/**
 * Process for inverting the image colors
 */
public class InvertColorProcess extends PixelProcess<Image> {

    /** Inverted image */
    private final Image invertedImage;

    /**
     * Creates a new invert colors process
     *
     * @param image
     */
    public InvertColorProcess(@Input("image") Image image) {
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
        this.invertedImage = resultImage;
        setFinisher(() -> {
            setOutput(invertedImage);
        });
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        Range<Integer> range = invertedImage.getPixelValueRange();
        int normalizedValue = value - range.getLower();
        int newValue = range.getLower() + ((int)range.getLength() + normalizedValue * -1);
        invertedImage.set(channel, x, y, newValue);
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
