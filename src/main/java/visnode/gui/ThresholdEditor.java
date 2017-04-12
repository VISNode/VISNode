package visnode.gui;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import visnode.commons.Threshold;

/**
 * Threshold editor
 */
public class ThresholdEditor extends JSlider implements ParameterComponent<Threshold> {

    /** Value */
    private Threshold value;
    
    /**
     * Creates a new threshold editor
     */
    public ThresholdEditor() {
        super(0, 255);
        value = new Threshold(128);
        setFocusable(false);
        setOpaque(false);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Threshold value) {
        this.value = value;
        super.setValue(value.intValue());
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        addChangeListener((ChangeEvent e) -> {
            valueListener.valueChanged(0, new Threshold(getValue()));
        });
    }
    
}
