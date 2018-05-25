package visnode.gui;

import javax.swing.JComponent;
import com.github.rxsling.RangedIntegerInput;

/**
 * Threshold editor
 */
public class PercentageEditor extends RangedIntegerInput implements ParameterComponent<Double> {

    /** Value */
    private Double value;
    
    /**
     * Creates a new threshold editor
     */
    public PercentageEditor() {
        super();
        lowerLimit(0).upperLimit(100);
        id("thresholdSelector");
        styleSheet(PercentageEditor.class.getResourceAsStream("/styles/visnode.css"));
        value = 0d;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Double value) {
        if (value != null) {
            this.value = value;
        } else {
            this.value = 0d;
        }
        super.value((int) (this.value * 100));
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        valueObservable().subscribe((v) -> {
            valueListener.valueChanged(0, (double)v / 100);
        });
    }
    
}
