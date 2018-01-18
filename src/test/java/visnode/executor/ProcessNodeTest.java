package visnode.executor;

import io.reactivex.Observable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Units test for ProcessNode
 */
public class ProcessNodeTest {

    /** Synchronizer */
    private Semaphor synchronizer;
    
    @Before
    public void setup() {
        synchronizer = new Semaphor();
    }
    
    /**
     * Tests the manual process execution
     */
    @Test
    public void testManualProcessExecution() {
        ProcessNode node = new ProcessNode(MockProcess.class);
        node.setInput("image", "Test");
        processAndWait(node);
        assertEquals("Test Modified", firstOutput(node, "output"));
    }

    /**
     * Tests the automatic process execution
     */
    @Test
    public void testAutomaticProcessExecution() {
        ProcessNode node = new ProcessNode(MockProcess.class);
        node.setInput("image", "Test");
        assertEquals("Test Modified", firstOutput(node, "output"));
    }

    /**
     * Tests the execution of a process that returns a observable of values
     */
    @Test
    public void testObservableProcessExecution() {
        ProcessNode node = new ProcessNode(MockObservableProcess.class);
        node.setInput("image", "Test");
        assertArrayEquals(new String[] {"Test 0", "Test 1", "Test 2", "Test 3", "Test 4"}, firstOutputs(node, "output", 5));
    }
    
    /**
     * Test return of input parameters
     */
    @Test
    public void testGetInputParameters() {
        ProcessNode node = new ProcessNode(TestProcess.class);
        assertEquals(Arrays.asList(new NodeParameter("image", String.class)), 
                node.getInputParameters());
    }
    
    /**
     * Test return of output parameters
     */
    @Test
    public void testGetOutputParameters() {
        ProcessNode process = new ProcessNode(TestProcess.class);
        assertEquals(Arrays.asList(new NodeParameter("output", String.class), new NodeParameter("image", String.class)),
                process.getOutputParameters());
    }
    
    /**
     * If a node has an observable output and the "observableOf" in the annotation, it should return the type of the 
     * observable, not Observable itself
     */
    @Test
    public void testGetOutputParametersOfObservableProcesses() {
        ProcessNode process = new ProcessNode(MockObservableProcess.class);
        assertEquals(Arrays.asList(new NodeParameter("output", String.class), new NodeParameter("image", String.class)),
                process.getOutputParameters());
    }

    /**
     * Runs the process and waits for its conclusion
     * 
     * @param node 
     */
    public void processAndWait(ProcessNode node) {
        node.process((t) -> {
            synchronizer.unlock();
        });
        synchronizer.join();
    }
    
    /**
     * Returns the first output
     * 
     * @param node
     * @param property
     * @return Object
     */
    public Object firstOutput(ProcessNode node, String property) {
        AtomicReference reference = new AtomicReference();
        Semaphor sync = new Semaphor();
        node.getOutput("output").subscribe((val) -> {
            reference.set(val);
            sync.unlock();
        });
        sync.join();
        return reference.get();
    }
    
    /**
     * Returns the first output
     * 
     * @param node
     * @param property
     * @return Object
     */
    public Object[] firstOutputs(ProcessNode node, String property, int n) {
        AtomicInteger index = new AtomicInteger();
        Semaphor sync = new Semaphor();
        Object[] result = new Object[n];
        node.getOutput("output").subscribe((val) -> {
            if (index.get() < n) {
                result[index.getAndAdd(1)] = val;
            }
            if (index.get() >= n) {
                sync.unlock();
            }
        });
        sync.join();
        return result;
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
    
    /**
     * Mock of a process that returns an observable of data
     */
    public static class MockObservableProcess implements visnode.pdi.Process {

        private final String image;
        private Observable<String> output;
        
        public MockObservableProcess(@Input("image") String image) {
            this.image = image;
        }

        @Override
        public void process() {
            output = Observable.range(0, 10).map((n) -> image + " " + n);
        }

        @Output("image")
        public String getImage() {
            return image;
        }

        @Output(value = "output", observableOf = String.class)
        public Observable<String> getOutput() {
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
    
    private class Semaphor {

        private final AtomicBoolean semaphor = new AtomicBoolean(true);
        
        public void unlock() {
            synchronized(semaphor) {
                semaphor.set(false);
                semaphor.notify();
            }
        }
        
        public void join() {
            try {
                synchronized(semaphor) {
                    if (semaphor.get()) {
                        semaphor.wait();
                    }
                }
            } catch (InterruptedException e) {}
        }
        
    }
    
}
