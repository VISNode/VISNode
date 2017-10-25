package visnode.application;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.paim.commons.ImageFactory;
import visnode.application.mvc.Model;
import visnode.application.mvc.PropertyEvent;
import visnode.commons.Threshold;
import visnode.executor.EditNodeDecorator;
import visnode.executor.InputNode;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;
import visnode.pdi.process.GrayscaleProcess;
import visnode.pdi.process.InformationProcess;
import visnode.pdi.process.ThresholdProcess;

/**
 * Model of the application
 */
public class VISNodeModel implements Model {

    /** Node network */
    private NodeNetwork network;
    /** File linked to the current project */
    private File linkedFile;
    /** User preferences */
    private UserPreferences userPreferences;

    /**
     * Creates a new model
     */
    public VISNodeModel() {
        network = new NodeNetwork();
        userPreferences = new UserPreferences();
        buildDummyModel();
    }
    
    private void buildDummyModel() {
        try {
            BufferedImage r = ImageIO.read(VISNodeModel.class.getResourceAsStream("/lena.jpg"));
            InputNode input = new InputNode(ImageFactory.buildRGBImage(r));
            ProcessNode grayScale = new ProcessNode(GrayscaleProcess.class);
            grayScale.addConnection("image", input, "image");

            ProcessNode information = new ProcessNode(InformationProcess.class);
            information.addConnection("image", grayScale, "image");

            ProcessNode threshold = new ProcessNode(ThresholdProcess.class);
            threshold.setInput("threshold", new Threshold(128));
            threshold.addConnection("image", grayScale, "image");

            OutputNode out = new OutputNode();
            out.addConnection("image", threshold, "image");
            
            network.add(new EditNodeDecorator(input, new Point(25, 12)));
            network.add(new EditNodeDecorator(grayScale, new Point(255, 12)));
            network.add(new EditNodeDecorator(threshold, new Point(460, 95)));
            network.add(new EditNodeDecorator(information, new Point(460, 381)));
            network.add(new EditNodeDecorator(out, new Point(760, 155)));
            
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
        }
    }

    /**
     * Returns the node network
     * 
     * @return NodeNetwork
     */
    public NodeNetwork getNetwork() {
        return network;
    }

    /**
     * Sets the node network
     * 
     * @param network 
     */
    public void setNetwork(NodeNetwork network) {
        NodeNetwork oldValue = this.network;
        this.network = network;
        fireEvent(new PropertyEvent("network", oldValue, network));
    }

    /**
     * Returns the linked file
     * 
     * @return File
     */
    public File getLinkedFile() {
        return linkedFile;
    }

    /**
     * Sets the linked file
     * 
     * @param linkedFile 
     */
    public void setLinkedFile(File linkedFile) {
        File oldValue = this.linkedFile;
        this.linkedFile = linkedFile;
        fireEvent(new PropertyEvent("linkedFile", oldValue, linkedFile));
    }

    /**
     * Returns the user preferences
     * 
     * @return UserPreferences
     */
    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    /**
     * Sets the user preferences
     * 
     * @param userPreferences 
     */
    public void setUserPreferences(UserPreferences userPreferences) {
        UserPreferences oldValue = this.userPreferences;
        this.userPreferences = userPreferences;
        fireEvent(new PropertyEvent("userPreferences", oldValue, userPreferences));
    }
    
}
