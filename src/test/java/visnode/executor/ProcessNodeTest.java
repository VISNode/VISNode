package visnode.executor;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Units test for ProcessNode
 */
public class ProcessNodeTest {

    /**
     * Tests the manual process execution
     */
    @Test
    public void testManualProcessExecution() {
        ProcessNode node = new ProcessNode(MockProcess.class);
        node.setInput("image", "Test");
//        node.process();
//        assertEquals("Test Modified", node.getOutput("output"));
    }

    /**
     * Tests the automatic process execution
     */
    @Test
    public void testAutomaticProcessExecution() {
        ProcessNode node = new ProcessNode(MockProcess.class);
        node.setInput("image", "Test");
//        assertEquals("Test Modified", node.getOutput("output"));
    }
    
    /**
     * Test return of input parameters
     */
    @Test
    @Ignore
    public void testGetInputParameters() {
        ProcessNode process = new ProcessNode(TestProcess.class);
//        assertEquals(Arrays.
//                asList(new NodeParameter("image", String.class)), 
//                process.getInputParameters());
    }

    /**
     * Test return of output parameters
     */
    @Test
    @Ignore
    public void testGetOutputParameters() {
        ProcessNode process = new ProcessNode(TestProcess.class);
        assertEquals(Arrays.asList(new NodeParameter("output", String.class),
                new NodeParameter("image", String.class)),
                process.getOutputParameters());
    }
    
    public static class MockProcess implements visnode.pdi.Process {

        private final String image;
        private String output;
        
        public MockProcess(@Input("image") String image) {
            this.image = image;
        }

        @Override
        public void process() {
            output = image + " Modified";
        }

        @Output("image")
        public String getImage() {
            return image;
        }

        @Output("output")
        public String getOutput() {
            return output;
        }

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
