/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import com.google.common.base.Objects;
import java.awt.Dimension;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import visnode.commons.gui.helpers.DocumentHelper;

/**
 * Numeric input
 */
public class NumericInput<V> extends JFormattedTextField implements DefaultInput<V, NumericInput> {

    /** Default input support */
    private final DefaultInputSupport inputSupport;
    /** Old value for events */
    private V oldValue;
    
    
    /**
     * Creates the ranged input
     */
    public NumericInput() {
        super();
        setValue(0);
        this.inputSupport = new DefaultNumericInputSupport(this);
        DocumentHelper.onChange(getDocument(), (evt) -> {
            firePropertyChange("value", oldValue, getNumericValue());
            oldValue = getNumericValue();
        });
        setPreferredSize(new Dimension(200, 25));
    }
    
    /**
     * Returns the numeric value
     * 
     * @return V
     */
    public V getNumericValue() {
        try {
            return (V) getFormatter().stringToValue(getText());
        } catch (ParseException e) {
            return (V) getValue();
        }
    }

    @Override
    public void setValue(Object value) {
        if (!Objects.equal(value, getValue())) {
            int caret = getCaretPosition();
            super.setValue(value);
            caret = Math.max(caret, getText().length());
            setCaretPosition(caret);
        }
    }
    
    @Override
    public DefaultInputSupport<V, NumericInput> getDefaultInputSupport() {
        return inputSupport;
    }
    
}
