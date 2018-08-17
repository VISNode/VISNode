package visnode.application;

import java.awt.Color;
import java.io.File;
import org.paim.commons.Image;
import visnode.commons.Angle;
import visnode.commons.DynamicValue;
import visnode.commons.Percentage;
import visnode.commons.ScriptValue;
import visnode.gui.ImageNodeComponent;
import visnode.commons.Threshold;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.NodeParameter;
import visnode.executor.OutputNode;
import visnode.gui.AngleEditor;
import visnode.gui.ColorEditor;
import visnode.gui.DoubleEditor;
import visnode.gui.DynamicValueComponent;
import visnode.gui.FileEditor;
import visnode.gui.InputEditor;
import visnode.gui.ScriptValueEditor;
import visnode.gui.IntegerEditor;
import visnode.gui.NotImplementedParameterComponent;
import visnode.gui.ParameterComponent;
import visnode.gui.PercentageEditor;
import visnode.gui.ScriptValueEditorDocumentationFactory;
import visnode.gui.ThresholdEditor;
import visnode.pdi.process.ImageInput;

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
        if (Image.class.isAssignableFrom(parameter.getType()) && type == ConnectionType.OUTPUT) {
            return new ImageNodeComponent();
        }
        if (parameter.getType().equals(DynamicValue.class) && node instanceof EditNodeDecorator && ((EditNodeDecorator) node).getDecorated() instanceof OutputNode) {
            return new DynamicValueComponent();
        }
        if (parameter.getType().equals(Threshold.class) && type == ConnectionType.INPUT) {
            return new ThresholdEditor();
        }
        if (parameter.getType().equals(File.class) && type == ConnectionType.INPUT) {
            return new FileEditor();
        }
        if (parameter.getType().equals(ImageInput.class) && type == ConnectionType.INPUT) {
            return new InputEditor();
        }
        if (parameter.getType().equals(ScriptValue.class) && type == ConnectionType.INPUT) {
            return new ScriptValueEditor(
                    ScriptValueEditorDocumentationFactory.create(node)
            );
        }
        if (parameter.getType().equals(Double.class) && type == ConnectionType.INPUT) {
            if (parameter.hasAnnotation(Percentage.class)) {
                return new PercentageEditor();
            } else {
                return new DoubleEditor();
            }
        }
        if (parameter.getType().equals(Integer.class) && type == ConnectionType.INPUT) {
            return new IntegerEditor();
        }
        if (parameter.getType().equals(Color.class) && type == ConnectionType.INPUT) {
            return new ColorEditor();
        }
        if (parameter.getType().equals(Angle.class) && type == ConnectionType.INPUT) {
            return new AngleEditor();
        }
        return new NotImplementedParameterComponent(parameter);
    }

}
