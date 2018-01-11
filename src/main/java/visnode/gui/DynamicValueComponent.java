package visnode.gui;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import org.paim.commons.Image;
import visnode.commons.DynamicValue;

/**
 * Dynamic value component
 */
public class DynamicValueComponent extends JComponent implements ParameterComponent<DynamicValue> {

    /** Value */
    private DynamicValue<Object> value;

    private JComponent component;

    /**
     * Creates a new dynamic value component
     */
    public DynamicValueComponent() {
        this.value = new DynamicValue("");
        initGui();
        update();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(150, 150));

    }

    /**
     * Updates the component value
     */
    private void update() {
        if (component != null) {
            remove(component);
        }
        if (value.isImage()) {
            ImageNodeComponent imageComponent = new ImageNodeComponent();

            imageComponent.setValue(value.get(Image.class));
            add(imageComponent);
            revalidate();
            component = imageComponent;
            return;
        }
        JTextArea field = new JTextArea();
        field.setText(value == null ? "" : String.valueOf(value.get()));
        field.setEditable(false);
        add(field);
        revalidate();
        component = field;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(DynamicValue value) {
        this.value = value;
        update();
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        // This component is read only
    }
}
