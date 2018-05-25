package visnode.pdi.process;

import java.awt.Color;
import org.paim.commons.BinaryImage;
import org.paim.commons.Image;
import org.paim.commons.ImageConverter;
import org.paim.commons.ImageFactory;
import org.paim.commons.Range;
import org.paim.pdi.PixelProcess;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Process responsible for merging images
 */
public class MergeImageProcess extends PixelProcess<Image> implements visnode.pdi.Process {

    /** Image base */
    private final Image imageBase;
    /** The image mask */
    private final BinaryImage mask;
    /** Color array */
    private final int[] color;
    
    public MergeImageProcess(@Input("background") Image background, @Input("mask") BinaryImage mask, @Input("image") Image image, @Input("color") Color color) {
        super(ImageFactory.buildRGBImage(ImageConverter.toBufferedImage(background == null ? ImageFactory.buildEmptyImage() : background)));
        this.imageBase = image;
        this.color = buildColor(color);
        this.mask = mask;
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        if (mask != null && mask.has(0, x, y) && mask.get(x, y)) {
            if (color != null) {
                image.set(channel, x, y, color[channel]);
            } else if (imageBase != null && imageBase.has(channel, x, y)) {
                image.set(channel, x, y, imageBase.get(channel, x, y));
            }
        }       
    }

    /**
     * Creates a color array
     * 
     * @param color
     * @return {@code int[]}
     */
    private int[] buildColor(Color color) {
        if (color == null) {
            return null;
        }
        return new int[] {
            color.getRed(),
            color.getGreen(),
            color.getBlue()
        };
    }

    /**
     * Returns the output image
     *
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return image;
    }

}
