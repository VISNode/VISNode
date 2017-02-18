package visnode.application;

import visnode.application.mvc.Model;
import visnode.application.mvc.PropertyEvent;

/**
 * Model of the application
 */
public class VISNodeModel implements Model {

    /** Node network */
    private NodeNetwork network;

    /**
     * Creates a new model
     */
    public VISNodeModel() {
        network = new NodeNetwork();
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
    
}
