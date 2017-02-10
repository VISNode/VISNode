package visnode.commons;

/**
 * Region of an image
 */
public class ImageRegion extends Image {
    
    /**
     * Creates a new image region
     * 
     * @param image 
     */
    public ImageRegion(Image image) {
        super(image);
    }
    
    /**
     * Creates a new image region
     * 
     * @param data
     * @param pixelRange
     */
    public ImageRegion(int[][][] data, Range<Integer> pixelRange) {
        super(data, pixelRange);
    }
    
}
