package visnode.gui;

import com.github.rxsling.RangedIntegerInput;
import javax.swing.JComponent;
import visnode.commons.Angle;

/**
 * Angle editor
 */
public class AngleEditor extends RangedIntegerInput implements ParameterComponent<Angle> {

    /** Value */
    private Angle value;
    
    /**
     * Creates a new angle editor
     */
    public AngleEditor() {
         super();
        lowerLimit(0).upperLimit(360);
        id("thresholdSelector");
        styleSheet(AngleEditor.class.getResourceAsStream("/styles/visnode.css"));
        value = new Angle(0);
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
         super.value(this.value.intValue());
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        valueObservable().subscribe((v) -> {
            valueListener.valueChanged(0, new Angle(v));
        });
    }
    
}
