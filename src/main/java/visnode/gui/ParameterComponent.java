package visnode.gui;

import javax.swing.JComponent;

/**
 * Parameter editor component
 * 
 * @param <T>
 */
public interface ParameterComponent<T> {

    /**
     * Returns the component
     * 
     * @return JComponent
     */
    public JComponent getComponent();
    
    /**
     * Updates the value
     * 
     * @param value 
     */
    public void setValue(T value);
    
    public void addValueListener(ValueListener<T> valueListener);
    
}
