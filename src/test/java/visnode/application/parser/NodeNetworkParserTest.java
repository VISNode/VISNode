/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.application.parser;

import java.awt.Point;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.paim.pdi.GaussianBlurProcess;
import visnode.application.NodeNetwork;
import visnode.application.NodeNetworkFactory;
import visnode.commons.MultiFileInput;
import visnode.executor.EditNodeDecorator;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;
import visnode.pdi.process.InputProcess;
import visnode.pdi.process.KirshProcess;
import visnode.pdi.process.ThresholdProcess;

/**
 *
 * @author Pichau
 */ 
public class NodeNetworkParserTest {
    
    /** Node network for testing */
    private NodeNetwork network;
    /** Parser for testing */
    private NodeNetworkParser parser;
    
    @Before
    public void setUp() {
        network = NodeNetworkFactory.createEmpty();
        parser = new NodeNetworkParser();
    }

    @Test
    public void testEmptyNetwork() {
        assertWriteAndRead(network);
    }

    @Test
    public void testSimpleNetwork() {
        network.add(new EditNodeDecorator(new OutputNode()));
        network.add(new EditNodeDecorator(new ProcessNode(ThresholdProcess.class), new Point(1, 2)));
        network.add(new EditNodeDecorator(new ProcessNode(KirshProcess.class), new Point(3, 4)));
        network.add(new EditNodeDecorator(new ProcessNode(GaussianBlurProcess.class), new Point(5, 6)));
        network.add(new EditNodeDecorator(new ProcessNode(InputProcess.class)));
        assertWriteAndRead(network);
    }

    @Test
    public void testInputWithInterfaces() {
        ProcessNode node = new ProcessNode(InputProcess.class);
        node.setInput("file", new MultiFileInput());
        network.add(new EditNodeDecorator(node));
        assertWriteAndRead(network);
    }

    /**
     * Assert a network by writing and reading it, checking if the network is still the same
     * 
     * @param original 
     */
    private void assertWriteAndRead(NodeNetwork original) {
        NodeNetwork result = parser.fromJson(parser.toJson(original));
        assertNetwork(original, result);
    }
    
    /**
     * Asserts a network
     * 
     * @param expected
     * @param result 
     */
    private void assertNetwork(NodeNetwork expected, NodeNetwork result) {
        assertEquals(expected.getNodes().size(), result.getNodes().size());
        for (int i = 0; i < result.getNodes().size(); i++) {
            assertNode(expected.getNodes().get(i), result.getNodes().get(i));
        }
    }

    /**
     * Asserts a node
     * 
     * @param expected
     * @param result 
     */
    private void assertNode(EditNodeDecorator expected, EditNodeDecorator result) {
        assertEquals(expected.getPosition(), result.getPosition());
        assertEquals(expected.getDecorated().getClass(), result.getDecorated().getClass());
    }
    
}
