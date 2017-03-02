
package visnode.gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import visnode.application.ConnectionType;
import visnode.executor.Node;
import visnode.executor.NodeParameter;

/**
 * Wrapper for a parameter component
 */
public class ParameterComponentWrapper extends JComponent {

    /** Component */
    private final ParameterComponent component;
    /** Node */
    private final Node node;
    /** Parameter */
    private final NodeParameter parameter;
    /** Parameter type */
    private final ConnectionType type;
    
    /**
     * Creates a new ParameterComponentWrapper
     * 
     * @param component
     * @param node
     * @param parameter
     * @param type 
     */
    public ParameterComponentWrapper(ParameterComponent component, Node node, NodeParameter parameter, ConnectionType type) {
        this.component = component;
        this.node = node;
        this.parameter = parameter;
        this.type = type;
        initGui();
        updateComponentValue();
        component.addValueListener((Object oldValue, Object newValue) -> {
            node.addParameter(parameter.getName(), newValue);
        });
        node.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            updateComponentValue();
        });
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(component.getComponent());
    }

    /**
     * Updates the component value
     */
    private void updateComponentValue() {
        component.setValue(node.getAttribute(parameter.getName()));
    }
    
}
