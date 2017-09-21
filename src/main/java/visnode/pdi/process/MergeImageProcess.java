package visnode.pdi.process;

import java.awt.Color;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.pdi.PixelProcess;
import visnode.application.OutputImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Process responsible for merging images
 */
public class MergeImageProcess extends PixelProcess<Image> implements visnode.pdi.Process {

    /** Background image */
    private final Image background;
    /** Image base */
    private final Image imageBase;
    /** Color array */
    private final int[] color;
    
    public MergeImageProcess(@Input("background") Image background, @Input("mask") Image mask, @Input("image") Image image, @Input("color") Color color) {
        super(mask == null ? ImageFactory.buildEmptyImage() : mask);
        this.background = ImageFactory.buildRGBImage(OutputImageFactory.getBuffered(background == null ? ImageFactory.buildEmptyImage() : background));
        this.imageBase = image;
        this.color = buildColor(color);
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        if (value == 1) {
            if (color != null) {
                background.set(channel, x, y, color[channel]);
            } else if (imageBase != null) {
                background.set(channel, x, y, imageBase.get(channel, x, y));
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
        return background;
    }

}
