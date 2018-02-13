package visnode.gui;

import javax.swing.JComponent;
import visnode.commons.Threshold;
import com.github.rxsling.RangedIntegerInput;

/**
 * Threshold editor
 */
public class ThresholdEditor extends RangedIntegerInput implements ParameterComponent<Threshold> {

    /** Value */
    private Threshold value;
    
    /**
     * Creates a new threshold editor
     */
    public ThresholdEditor() {
        super();
        lowerLimit(0).upperLimit(255);
        id("thresholdSelector");
        styleSheet(ThresholdEditor.class.getResourceAsStream("/styles/visnode.css"));
        value = new Threshold(128);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Threshold value) {
        if (value != null) {
            this.value = value;
        } else {
            this.value = new Threshold(0);
        }
        super.value(this.value.intValue());
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        valueObservable().subscribe((v) -> {
            valueListener.valueChanged(0, new Threshold(v));
        });
    }
    
}
