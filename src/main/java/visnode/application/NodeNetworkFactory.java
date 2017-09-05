
package visnode.application;

import java.awt.Point;
import org.paim.commons.ImageFactory;
import visnode.commons.Threshold;
import visnode.executor.EditNodeDecorator;
import visnode.executor.InputNode;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;
import visnode.pdi.process.GrayscaleProcess;
import visnode.pdi.process.InformationProcess;
import visnode.pdi.process.ThresholdProcess;

/**
 *
 * @author Nícolas Pohren
 */
public class NodeNetworkFactory {

    public static NodeNetwork createEmpty() {
        NodeNetwork network = new NodeNetwork();
        InputNode input = new InputNode(ImageFactory.buildEmptyImage());
        OutputNode output = new OutputNode();
        network.add(new EditNodeDecorator(input, new Point(25, 25)));
        network.add(new EditNodeDecorator(output, new Point(225, 25)));
        return network;
    }
    
}
