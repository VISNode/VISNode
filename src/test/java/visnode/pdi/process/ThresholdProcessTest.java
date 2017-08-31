package visnode.pdi.process;

import org.junit.Test;
import org.torax.commons.Image;
import org.torax.commons.Range;
import visnode.ImageAssert;
import visnode.commons.Threshold;

/**
 * Unit tests of class ThresholdProcess
 */
public class ThresholdProcessTest {

    /**
     * Test on a image that has a single channel
     */
    @Test
    public void testSingleChannelImage() {
        Image image = new Image(new int[][][]{
            {
                {0, 10, 20, 30, 40, 48, 49, 50, 51, 52, 60, 70, 80, 90, 100}
            }
        }, new Range<>(0, 100));
        ThresholdProcess process = new ThresholdProcess(image, new Threshold(50));
        process.process();
        Image output = process.getImage();
        ImageAssert.assertImage(new Image(new int[][][]{
            {
                {0, 0, 0, 0, 0, 0, 0, 100, 100, 100, 100, 100, 100, 100, 100}
            }
        }, new Range<>(0, 100)), output);
    }

}
