package visnode.application.mvc;

/**
 * Property change event
 */
public class PropertyEvent extends EventObject {

    /** Property name */
    public final String propertyName;
    /** Old value */
    public final Object oldValue;
    /** New value */
    public final Object newValue;

    /**
     * Creates a new property event
     * 
     * @param propertyName
     * @param oldValue
     * @param newValue 
     */
    public PropertyEvent(String propertyName, Object oldValue, Object newValue) {
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns the property name
     * 
     * @return String
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Returns the old value
     * 
     * @return Object
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * Returns the new value
     * 
     * @return Object
     */
    public Object getNewValue() {
        return newValue;
    }
    
}
