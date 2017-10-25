package visnode.application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.paim.commons.BinaryImage;
import org.paim.commons.ConverterWLWW;
import org.paim.commons.Image;

/**
 * Output image factory
 */
public class OutputImageFactory {

    private static final int WINDOW_LEVEL = -400;
    private static final int WINDOW_WIDTH = 1500;

    /**
     * Creates a buffered image
     *
     * @param image
     * @return BufferedImage
     */
    public static BufferedImage getBuffered(Image image) {
        if (image == null) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
        if (image.getPixelValueRange().isDICOM()) {
            return ConverterWLWW.getBufferedImage(image, WINDOW_LEVEL, WINDOW_WIDTH);
        }
        BufferedImage buff = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int r, g, b;
                if (image instanceof BinaryImage) {
                    r = g = b = (((BinaryImage) image).get(x, y) ? 255 : 0);
                } else if (image.getPixelValueRange().isBinary()) {
                    r = g = b = (image.get(0, x, y) == 1 ? 255 : 0);
                } else {

                    if (image.getChannelCount() == Image.CHANNELS_RGB) {
                        r = image.get(Image.CHANNEL_RED, x, y);
                        g = image.get(Image.CHANNEL_GREEN, x, y);
                        b = image.get(Image.CHANNEL_BLUE, x, y);
                    } else {
                        r = image.get(0, x, y);
                        g = image.get(0, x, y);
                        b = image.get(0, x, y);
                    }
                }
                buff.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return buff;
    }

}
