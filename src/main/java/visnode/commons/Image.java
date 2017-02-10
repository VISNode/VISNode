package visnode.commons;

/**
 * Image representation
 */
public class Image {

    /** Number of channels for RGB images */
    public static final int CHANNELS_RGB = 3;
    /** Number of channels for gray scale images */
    public static final int CHANNELS_GRAYSCALE = 1;
    /** Channel - Red */
    public static final int CHANNEL_RED = 0;
    /** Channel - Green */
    public static final int CHANNEL_GREEN = 1;
    /** Channel - Blue */
    public static final int CHANNEL_BLUE = 2;
    /** Channel - Gray (For gray scales) */
    public static final int CHANNEL_GRAY = 0;

    /** Image data */
    private final int[][][] data;
    /** Range of possible values in a pixel */
    private final Range<Integer> pixelRange;

    /**
     * Creates a new image based on another image
     *
     * @param image
     */
    public Image(Image image) {
        this(image.data, image.pixelRange);
    }

    /**
     * Creates a new image
     *
     * @param data
     * @param pixelRange
     */
    public Image(int[][][] data, Range<Integer> pixelRange) {
        this.data = ArrayHelper.copy(data);
        this.pixelRange = pixelRange;
    }

    /**
     * Returns a pixel value
     *
     * @param channel
     * @param x
     * @param y
     * @return int
     */
    public int get(int channel, int x, int y) {
        return data[channel][x][y];
    }

    /**
     * Set a pixel value
     *
     * @param channel
     * @param x
     * @param y
     * @param value
     */
    public void set(int channel, int x, int y, int value) {
        data[channel][x][y] = value;
    }

    /**
     * Returns the image data
     *
     * @return int[][][]
     */
    public int[][][] getData() {
        return data;
    }

    /**
     * Returns the number of channels in the image
     *
     * @return int
     */
    public int getChannelCount() {
        return data.length;
    }

    /**
     * Returns the width of the image
     *
     * @return int
     */
    public int getWidth() {
        return data[0].length;
    }

    /**
     * Returns the height of the image
     *
     * @return int
     */
    public int getHeight() {
        return data[0][0].length;
    }

    /**
     * Returns the pixel value range
     *
     * @return {@code Range<Integer>}
     */
    public Range<Integer> getPixelValueRange() {
        return pixelRange;
    }

}
