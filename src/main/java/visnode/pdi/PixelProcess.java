package visnode.pdi;

import org.torax.commons.Image;

/**
 * Process that iterates over a image pixels
 * 
 * @param <O>
 */
public abstract class PixelProcess<O> extends ImageProcess<O> {
    
    /**
     * Creates a new process that iterates over the image pixels
     * 
     * @param image 
     */
    public PixelProcess(Image image) {
        super(image);
    }

    @Override
    public void processImage() {
        for (int channel = 0; channel < image.getChannelCount(); channel++) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    process(channel, x, y, image.get(channel, x, y));
                }    
            }
        }
    }
    
    /**
     * Process a pixel
     * 
     * @param channel
     * @param x
     * @param y
     * @param value
     */
    protected abstract void process(int channel, int x, int y, int value);
    
}
