package visnode.gui;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import visnode.commons.Angle;

/**
 * Angle editor
 */
public class AngleEditor extends JSlider implements ParameterComponent<Angle> {

    /** Value */
    private Angle value;
    
    /**
     * Creates a new angle editor
     */
    public AngleEditor() {
        super(0, 360);
        value = new Angle(0);
        setValue(value);
        setFocusable(false);
        setOpaque(false);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Angle value) {
        if (value != null) {
            this.value = value;
        } else {
            this.value = new Angle(0);
        }
        super.setValue(this.value.intValue());
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        addChangeListener((ChangeEvent e) -> {
            valueListener.valueChanged(0, new Angle(getValue()));
        });
    }
    
}
