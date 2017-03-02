package visnode.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import visnode.executor.NodeParameter;

/**
 * Parameter component for not implemented classes
 */
public class NotImplementedParameterComponent extends JLabel implements ParameterComponent {

    /**
     * Creates a new parameter component for not implemented classes
     * 
     * @param parameter 
     */
    public NotImplementedParameterComponent(NodeParameter parameter) {
        super(String.format("<Not impl.: %1$s %2$s>", parameter.getName(), parameter.getType().getSimpleName()));
    }
    
    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Object value) {
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
    }
    
}
