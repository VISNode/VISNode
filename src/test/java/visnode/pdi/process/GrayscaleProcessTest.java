package visnode.pdi.process;

import org.junit.Test;
import visnode.ImageAssert;
import visnode.commons.Image;
import visnode.commons.Range;

/**
 * Unit tests of class GrayscaleProcess
 */
public class GrayscaleProcessTest {
    
    /**
     * Test on a image 
     */
    @Test
    public void testProcess() {
        Image image = new Image(new int[][][]{
            {
                {0, 10, 20, 30, 40, 48, 49, 50, 51, 52, 60, 70, 80, 90, 100}
            },
            {
                {0, 15, 3, 3, 4, 8, 9, 59, 60, 2, 30, 75, 40, 87, 10}
            },
            {
                {0, 3, 1, 30, 41, 50, 4, 50, 1, 58, 60, 70, 8, 95, 100}
            }
        }, new Range<>(0, 100));
        
        GrayscaleProcess process = new GrayscaleProcess(image);
        process.process();
        Image output = process.getImage();
        ImageAssert.assertImage(new Image(new int[][][]{
            {
                {0, 9, 7, 21, 27, 34, 20, 51, 37, 36, 50, 71, 41, 90, 69}
            }
        }, new Range<>(0, 100)), output);
    }    
    
}
