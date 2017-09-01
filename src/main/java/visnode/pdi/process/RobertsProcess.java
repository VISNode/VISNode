package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.ImageProcess;

/**
 *
 * @author Nícolas Pohren
 */
public class RobertsProcess extends ImageProcess<Image> {

    /** Gray scale image */
    private final Image outputImage;
    /** X Mask */
    private final double[][] xMask;
    /** Y Mask */
    private final double[][] yMask;

    /**
     * Creates a new Roberts process
     * 
     * @param image 
     */
    public RobertsProcess(@Input("image") Image image) {
        super(image);
        outputImage = ImageFactory.buildEmptyImage(image);
        xMask = new double[][]{{0, 0, 0}, {0, -1, 0}, {0, 0, 1}};
        yMask = new double[][]{{0, 0, 0}, {0, 0, -1}, {0, 1, 0}};
        setFinisher(() -> {
            setOutput(outputImage);
        });
    }

    @Override
    protected void processImage() {
        for (int channel = 0; channel < image.getChannelCount(); channel++) {
            for (int x = 1; x < image.getWidth() - 1; x++) {
                for (int y = 1; y < image.getHeight() - 1; y++) {
                    int newValue = computePixel(channel, x, y);
                    outputImage.set(channel, x, y, newValue);
                }
            }
        }
    }

    /**
     * Computes the pixel
     * 
     * @param channel
     * @param x
     * @param y
     * @return int
     */
    private int computePixel(int channel, int x, int y) {
        double gradienteX = 0.0d;
        double gradienteY = 0.0d;
        for (int lx = 0; lx < 3; lx++) {
            for (int ly = 0; ly < 3; ly++) {
                int valor = image.get(channel, x + lx - 1, y + ly - 1);
                gradienteX += valor * xMask[lx][ly];
                gradienteY += valor * yMask[lx][ly];

            }
        }
        return (int) Math.sqrt(Math.pow(gradienteX, 2) + Math.pow(gradienteY, 2));
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
