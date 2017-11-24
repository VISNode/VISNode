package visnode.gui;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import visnode.commons.Threshold;
import visnode.commons.gui.Inputs;
import visnode.commons.gui.Panel;
import visnode.commons.gui.RangedIntegerInput;

/**
 * Threshold editor
 */
public class ThresholdEditor extends Panel implements ParameterComponent<Threshold> {

    /** Value */
    private Threshold value;
    
    /**
     * Creates a new threshold editor
     */
    public ThresholdEditor() {
        super();
        value = new Threshold(128);
        setLayout(new BorderLayout());
        put(Inputs.rangedInteger().id("field"));
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
        findId("field").as(RangedIntegerInput.class).value(this.value.intValue());
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        findId("field").as(RangedIntegerInput.class).valueObservable().subscribe((v) -> {
            valueListener.valueChanged(0, new Threshold(v));
        });
    }
    
}
