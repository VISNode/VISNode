package visnode.application;

import java.awt.Point;
import org.paim.commons.ImageFactory;
import visnode.executor.EditNodeDecorator;
import visnode.executor.InputNode;
import visnode.executor.OutputNode;

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
        InputNode input = new InputNode();
        OutputNode output = new OutputNode();
        network.add(new EditNodeDecorator(input, new Point(50, 50)));
        network.add(new EditNodeDecorator(output, new Point(250, 50)));
        return network;
    }

}
