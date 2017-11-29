/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.pdi.process;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;
import org.paim.commons.BinaryImage;
import org.paim.commons.Image;
import org.paim.commons.Range;
import org.paim.pdi.ExtractedObject;
import org.paim.pdi.ObjectList;
import visnode.commons.ScriptValue;

/**
 * Unit tests for the object extraction process
 */
public class ObjectExtractionProcessTest {

    @Test
    public void testProcess() {
        ObjectExtractionProcess process = new ObjectExtractionProcess(createTestObjectList(), new ScriptValue("function(list) { return list; }"));
        BinaryImage outputImage = process.getImage();
        assertNotNull(outputImage);
    }
    
    /**
     * Creates a test Object List
     * 
     * @return ObjectList
     */
    private ObjectList createTestObjectList() {
        Image image = new Image(new int[][][] {{
            {1, 2, 3},
            {4, 2, 3},
            {0, 2, 0}
        }}, new Range<>(0, 100));
        return new ObjectList(Arrays.asList(
                new ExtractedObject(1, image), 
                new ExtractedObject(2, image), 
                new ExtractedObject(3, image), 
                new ExtractedObject(4, image), 
                new ExtractedObject(5, image)));
    }

}
