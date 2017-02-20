package visnode.application;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import visnode.executor.EditNodeDecorator;
import visnode.gui.JNode;
import visnode.gui.JNodeContainer;

/**
 * Network editor
 */
public class NetworkEditor extends JComponent {

    /** Model */
    private final NodeNetwork model;
    /** Node container */
    private JNodeContainer nodeContainer;

    /**
     * Creates a new Network Editor
     * 
     * @param model 
     */
    public NetworkEditor(NodeNetwork model) {
        this.model = model;
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(buildNodeContainer());
        fullUpdate();
    }

    /**
     * Builds the node container
     * 
     * @return JComponent
     */
    private JComponent buildNodeContainer() {
        nodeContainer = new JNodeContainer();
        return nodeContainer;
    }

    /**
     * Fully updates the view based on the model
     */
    private void fullUpdate() {
        nodeContainer.removeAll();
        for (EditNodeDecorator node : model.getNodes()) {
            nodeContainer.add(buildNodeFor(node));
        }
    }

    /**
     * Creates a JNode for the model Node
     * 
     * @param node
     * @return JComponent
     */
    private JComponent buildNodeFor(EditNodeDecorator node) {
        return new NodeView(node);
    }
    
}
