package visnode.commons.reflection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Property
 * 
 * @author Jouwee
 */
public class RProperty {
    
    /** Getter method */
    private final Method getter;
    
    /**
     * Crates a new property from a descriptor
     * 
     * @param descriptor 
     */
    public RProperty(PropertyDescriptor descriptor) {
        getter = descriptor.getReadMethod();
    }
    
    /**
     * Returns the property value from an object
     * 
     * @param object
     * @return Object
     * @throws ReflectionException
     */
    public Object get(Object object) throws ReflectionException {
        try {
            return getter.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ReflectionException(ex);
        }
    }
    
}
