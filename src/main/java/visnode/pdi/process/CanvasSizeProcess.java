package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.pdi.PixelProcess;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Canvas size process
 */
public class CanvasSizeProcess  extends PixelProcess<Image> implements visnode.pdi.Process {

    /** Output image */
    private final Image output;
    /** OffsetX */
    private final Integer offsetX;
    /** OffsetY */
    private final Integer offsetY;
    
    /**
     * Creates a new canvas size process
     * 
     * @param image
     * @param width
     * @param height
     * @param offsetX
     * @param offsetY 
     */
    public CanvasSizeProcess(@Input("image") Image image, 
            @Input("width") Integer width, @Input("height") Integer height,
            @Input("offsetX") Integer offsetX, @Input("offsetY") Integer offsetY) {
        super(image == null ? ImageFactory.buildEmptyImage() : image);
        if (width == null || width == 0) {
            width = this.image.getWidth();
        }
        if (height == null || height == 0) {
            height = this.image.getHeight();
        }
        this.offsetX = (offsetX == null || offsetX == 0) ? (width - this.image.getWidth()) / 2 : offsetX;
        this.offsetY = (offsetY == null || offsetY == 0) ? (height - this.image.getHeight()) / 2 : offsetY;
        this.output = ImageFactory.buildEmptyImage(
                this.image.getChannelCount(), 
                width, 
                height, 
                this.image.getPixelValueRange()
        );
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        output.set(channel, output.limitX(x + offsetX), output.limitY(y + offsetY), value);
    }

    /**
     * Returns the output image
     *
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return output;
    }
    
}
