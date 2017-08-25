
package visnode.application;

import visnode.commons.DynamicNodeValue;
import visnode.gui.ImageNodeComponent;
import visnode.commons.Image;
import visnode.commons.Threshold;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.NodeParameter;
import visnode.executor.OutputNode;
import visnode.gui.DoubleEditor;
import visnode.gui.DynamicNodeValueEditor;
import visnode.gui.IntegerEditor;
import visnode.gui.NotImplementedParameterComponent;
import visnode.gui.ParameterComponent;
import visnode.gui.ThresholdEditor;

/**
 * Parameter component factory
 */
public class ParameterComponentFactory {

    /**
     * Creates a new component for parameter visualization and or/editing
     * 
     * @param node
     * @param parameter
     * @param type
     * @return ParameterComponent
     */
    public ParameterComponent create(Node node, NodeParameter parameter, ConnectionType type) {
        if (parameter.getType().equals(Image.class) && type == ConnectionType.OUTPUT) {
            return new ImageNodeComponent();
        }
        if (parameter.getType().equals(Image.class) && node instanceof OutputNode) {
            return new ImageNodeComponent();
        }
        if (parameter.getType().equals(Image.class) && node instanceof EditNodeDecorator && ((EditNodeDecorator)node).getDecorated() instanceof OutputNode) {
            return new ImageNodeComponent();
        }
        if (parameter.getType().equals(Threshold.class) && type == ConnectionType.INPUT) {
            return new ThresholdEditor();
        }
        if (parameter.getType().equals(DynamicNodeValue.class) && type == ConnectionType.INPUT) {
            return new DynamicNodeValueEditor();
        }
        if (parameter.getType().equals(Double.class)) {
            return new DoubleEditor();
        }
        if (parameter.getType().equals(Integer.class)) {
            return new IntegerEditor();
        }
        return new NotImplementedParameterComponent(parameter);
    }

}
