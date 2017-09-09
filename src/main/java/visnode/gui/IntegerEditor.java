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
 * Editor for Integer values
 */
public class IntegerEditor extends JComponent implements ParameterComponent<Integer> {

    /** Field */
    private final JFormattedTextField field;
    
    /**
     * Creates a new integer editor
     */
    public IntegerEditor() {
        super();
        setLayout(new BorderLayout());
        field = new JFormattedTextField(new DecimalFormat("#0"));
        add(field);
        setValue(0);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Integer value) {
        if (value == null) {
            value = 0;
        }
        field.setValue(value);
    }

    /**
     * Returns the value
     * 
     * @return Integer
     */
    public Integer getValue() {
        return ((Number)field.getValue()).intValue();
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        field.addActionListener((evt) -> {
            valueListener.valueChanged(0, getValue());
        });
    }
    
}
