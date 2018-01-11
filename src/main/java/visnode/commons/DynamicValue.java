package visnode.commons;

import org.paim.commons.Image;

/**
 *
 * @param <T>
 */
public class DynamicValue<T> {

    
    private final T value;

    public DynamicValue(T value) {
        this.value = value;
    }
    

    public T get() {
        return value;
    }
   
    public <J extends Object> J get(Class<J> type) {
        return type.cast(value) ;
    }
    
    public boolean isImage() {
        return value instanceof Image;
    }
    
    
}
