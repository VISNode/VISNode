package visnode.pdi.process;

import org.torax.commons.Image;
import org.torax.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.PixelProcess;

/**
 * Process for converting a image to gray scale
 */
public class GrayscaleProcess extends PixelProcess<Image> {

    /** Gray scale image */
    private final Image grayImage;

    /**
     * Creates a new gray scale process
     *
     * @param image
     */
    public GrayscaleProcess(@Input("image") Image image) {
        super(image);
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
        int grayValue = grayImage.get(Image.CHANNEL_GRAY, x, y);
        grayImage.set(Image.CHANNEL_GRAY, x, y, grayValue + (value / 3));
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
