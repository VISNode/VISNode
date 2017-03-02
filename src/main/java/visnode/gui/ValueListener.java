
package visnode.gui;

import java.util.EventListener;

/**
 * Value listener
 */
public interface ValueListener<T> extends EventListener {

    /**
     * Event fired when the value has changed
     * 
     * @param oldValue
     * @param newValue 
     */
    public void valueChanged(T oldValue, T newValue);
    
}
