package visnode.gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import visnode.application.ConnectionType;
import visnode.application.ExceptionHandler;
import visnode.commons.TypeConverter;
import visnode.executor.AttacherNode;
import visnode.executor.ConnectionChangeEvent;
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
    /** Old value */
    private Object oldValue;

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
            node.setInput(parameter.getName(), newValue);
        });
        if (node instanceof AttacherNode) {
            AttacherNode attacherNode = (AttacherNode) node;
            attacherNode.addConnectionChangeListener((ConnectionChangeEvent evt) -> {
                if (evt.getParameter().getName().equals(parameter.getName())) {
                    component.setEnabled(!evt.isCreating());
                }
            });
        }
        node.addInputChangeListener((PropertyChangeEvent evt) -> {
            SwingUtilities.invokeLater(this::updateComponentValue);
        });
        node.addOutputChangeListener((PropertyChangeEvent evt) -> {
            SwingUtilities.invokeLater(this::updateComponentValue);
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
        try {
            if (type == ConnectionType.INPUT) {
                updateComponentValue(node.getInput(parameter.getName()));
            } else {
                node.getOutput(parameter.getName()).subscribe((value) -> {
                    updateComponentValue(value);
                });
            }
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
        }
    }

    /**
     * Updates the component value
     */
    private void updateComponentValue(Object value) {
        TypeConverter converter = new TypeConverter();
        if (value == null || value == oldValue) {
            return;
        }
        oldValue = value;
        component.setValue(converter.convert(value, parameter.getType()));
    }

}
