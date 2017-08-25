/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

/**
 * Editor for double values
 */
public class DoubleEditor extends JComponent implements ParameterComponent<Double> {

    /** Field */
    private final JFormattedTextField field;
    
    /**
     * Creates a new threshold editor
     */
    public DoubleEditor() {
        super();
        setLayout(new BorderLayout());
        field = new JFormattedTextField(new DecimalFormat("#0.0####"));
        add(field);
        setValue(0d);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Double value) {
        if (value == null) {
            value = 0d;
        }
        field.setValue(value);
    }

    /**
     * Returns the value
     * 
     * @return Double
     */
    public Double getValue() {
        return ((Number) field.getValue()).doubleValue();
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        field.addActionListener((evt) -> {
            valueListener.valueChanged(0, getValue());
        });
    }
    
}
