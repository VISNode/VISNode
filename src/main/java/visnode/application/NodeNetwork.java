package visnode.application;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import visnode.application.mvc.Model;
import visnode.commons.ImageFactory;
import visnode.executor.EditNodeDecorator;
import visnode.executor.InputNode;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;
import visnode.pdi.process.GrayscaleProcess;
import visnode.pdi.process.InformationProcess;
import visnode.pdi.process.ThresholdProcess;

/**
 * Node network
 */
public class NodeNetwork implements Model {

    /** Nodes */
    private final List<EditNodeDecorator> nodes;

    /**
     * Creates a new node network
     */
    public NodeNetwork() {
        nodes = new ArrayList<>();
        try {
            BufferedImage r = ImageIO.read(new File(getClass().getResource("/lena.jpg").getFile()));
            InputNode input = new InputNode(ImageFactory.buildRGBImage(r));

            ProcessNode grayScale = new ProcessNode(GrayscaleProcess.class);
            grayScale.addConnection("image", input, "image");

            ProcessNode information = new ProcessNode(InformationProcess.class);
            information.addConnection("image", grayScale, "image");

            ProcessNode threshold = new ProcessNode(ThresholdProcess.class);
            threshold.addConnection("threshold", information, "average");
            threshold.addConnection("image", grayScale, "image");

            OutputNode out = new OutputNode();
            out.addConnection("image", threshold, "image");
            
            nodes.add(new EditNodeDecorator(input, new Point(50, 125)));
            nodes.add(new EditNodeDecorator(grayScale, new Point(200, 50)));
            nodes.add(new EditNodeDecorator(information, new Point(200, 200)));
            nodes.add(new EditNodeDecorator(threshold, new Point(350, 125)));
            nodes.add(new EditNodeDecorator(out, new Point(500, 125)));
            
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
        }
    }

    /**
     * Returns the nodes of the network
     * 
     * @return {@code List<EditNodeDecorator>}
     */
    public List<EditNodeDecorator> getNodes() {
        return new ArrayList<>(nodes);
    }
    
}
