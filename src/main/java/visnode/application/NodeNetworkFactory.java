package visnode.application;

import java.awt.Point;
import visnode.commons.ImageFactory;
import visnode.commons.Threshold;
import visnode.executor.EditNodeDecorator;
import visnode.executor.InputNode;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;
import visnode.pdi.process.GrayscaleProcess;
import visnode.pdi.process.InformationProcess;
import visnode.pdi.process.ThresholdProcess;

/**
 * Factory for node networks
 */
public class NodeNetworkFactory {
    
    /**
     * Creates a empty node network
     * 
     * @return NodeNetwork
     */
    public static NodeNetwork createEmtpy() {
        NodeNetwork network = new NodeNetwork();
        InputNode input = new InputNode(ImageFactory.buildEmptyImage());
        OutputNode output = new OutputNode();
        network.add(new EditNodeDecorator(input, new Point(50, 50)));
        network.add(new EditNodeDecorator(output, new Point(250, 50)));
        return network;
    }
    
}
