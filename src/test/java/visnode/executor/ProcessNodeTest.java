package visnode.executor;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Units test for ProcessNode
 */
public class ProcessNodeTest {

    /**
     * Test return of input parameters
     */
    @Test
    public void testGetInputParameters() {
        ProcessNode process = new ProcessNode(TestProcess.class);
        Assert.assertEquals(Arrays.asList("image"), process.getInputParameters());
    }

    /**
     * Test return of output parameters
     */
    @Test
    public void testGetOutputParameters() {
        ProcessNode process = new ProcessNode(TestProcess.class);
        Assert.assertEquals(Arrays.asList("output", "image"),
                process.getOutputParameters());
    }

    @Test
    public void testGetAttribute() {
        ProcessNode process = new ProcessNode(TestProcess.class);
        process.addParameter("image", "");
        Assert.assertEquals("output", process.getAttribute("output"));
        Assert.assertEquals("image", process.getAttribute("image"));
    }

    public static class TestProcess implements visnode.pdi.Process {

        public TestProcess(@Input("image") String image) {
        }

        @Override
        public void process() {
        }

        @Output("image")
        public String getImage() {
            return "image";
        }

        @Output("output")
        public String getOutput() {
            return "output";
        }

    }
}
