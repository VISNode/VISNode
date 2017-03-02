package visnode.pdi.process;

import visnode.commons.Image;
import visnode.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.commons.Range;
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
        this.grayImage = ImageFactory.
                buildEmptyImage(Image.CHANNELS_GRAYSCALE,
                        image.getWidth(),
                        image.getHeight(),
                        image.getPixelValueRange());
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
