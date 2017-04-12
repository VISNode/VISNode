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
    
    /**
     * Adds the value listener
     * 
     * @param valueListener 
     */
    public void addValueListener(ValueListener<T> valueListener);
    
    /**
     * Sets if the editor is enabled
     * 
     * @param enabled 
     */
    public void setEnabled(boolean enabled);
    
}
