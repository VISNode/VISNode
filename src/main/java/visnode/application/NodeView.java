package visnode.application;

import javax.swing.JLabel;
import visnode.executor.EditNodeDecorator;
import visnode.executor.ProcessNode;
import visnode.gui.JNode;
import visnode.gui.JNodeConnector;

/**
 * View for the node
 */
public class NodeView extends JNode {

    /** Model */
    private final EditNodeDecorator model;

    /**
     * Creates the view
     * 
     * @param model 
     */
    public NodeView(EditNodeDecorator model) {
        super();
        this.model = model;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setBounds(model.getPosition().x, model.getPosition().y, 100, 150);
        createConnectors();
    }

    /**
     * Creates the connector
     */
    private void createConnectors() {
        if (model.getDecorated() instanceof ProcessNode) {
            createConnectors((ProcessNode)model.getDecorated());
        }
    }

    /**
     * Creates the connectors 
     * 
     * @param node 
     */
    private void createConnectors(ProcessNode node) {
        for (String inputParameter : node.getInputParameters()) {
            add(new JNodeConnector(new JLabel(inputParameter), JNodeConnector.Configuration.LEFT));
        }
        for (String inputParameter : node.getOutputParameters()) {
            add(new JNodeConnector(new JLabel(inputParameter), JNodeConnector.Configuration.RIGHT));
        }
    }
    
}
