package visnode.pdi.process;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import visnode.commons.Image;
import visnode.commons.Range;

/**
 * Unit tests of class InformationsProcess
 */
public class InformationProcessTest {

    /**
     * Test average calculation
     */
    @Test
    public void testGetAverage() {
        Image image = new Image(new int[][][]{
            {
                {0, 10, 20, 30, 40},
                {0, 20, 24, 3, 90}
            },
            {
                {8, 3, 42, 32, 4},
                {7, 45, 4, 36, 9}
            }
        }, new Range<>(0, 50));
        InformationProcess process = new InformationProcess(image);
        process.process();
        assertEquals(21, process.getAverage());
    }

}
