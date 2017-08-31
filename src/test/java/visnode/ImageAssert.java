package visnode;

import static junit.framework.Assert.*;
import org.torax.commons.Image;

/**
 * Class for asserting images
 */
public class ImageAssert {

    /**
     * Asserts an image
     * 
     * @param expected
     * @param result 
     */
    public static final void assertImage(Image expected, Image result) {
        if (expected == null || result == null) {
            if (expected == null && result == null) {
                return;
            }
            fail();
        }
        assertImageMetadata(expected, result);
        assertImageData(expected.getData(), result.getData());
    }

    /**
     * Asserts the image meta data
     * 
     * @param expected
     * @param result 
     */
    public static void assertImageMetadata(Image expected, Image result) {
        assertEquals(expected.getChannelCount(), result.getChannelCount());
        assertEquals(expected.getWidth(), result.getWidth());
        assertEquals(expected.getHeight(), result.getHeight());
        assertEquals(expected.getPixelValueRange(), result.getPixelValueRange());
    }

    /**
     * Asserts the image raw data
     * 
     * @param expected
     * @param result 
     */
    public static void assertImageData(int[][][] expected, int[][][] result) {
        for (int channel = 0; channel < expected.length; channel++) {
            for (int x = 0; x < expected[channel].length; x++) {
                for (int y = 0; y < expected[channel][x].length; y++) {
                    assertEquals(expected[channel][x][y], result[channel][x][y]);
                }    
            }
        }
    }
    
}
