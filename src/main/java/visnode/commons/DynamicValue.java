package visnode.commons;

import org.paim.commons.BinaryImage;
import org.paim.commons.Image;

/**
 * Dynamic value
 *
 * @param <T>
 */
public class DynamicValue<T> {

    /** Value */
    private final T value;

    public DynamicValue(T value) {
        this.value = value;
    }
    
    public DynamicValue(DynamicValue value) {
        this((T) value.get());
    }

    /**
     * Returns the value
     *
     * @return
     */
    public T get() {
        return value;
    }

    /**
     * Returns the value
     *
     * @param <J>
     * @param type
     * @return J
     */
    public <J extends Object> J get(Class<J> type) {
        return type.cast(value);
    }

    /**
     * Returns true if the value is a image
     *
     * @return boolean
     */
    public boolean isImage() {
        if (value == null) {
            return false;
        }
        return Image.class.isAssignableFrom(value.getClass());
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

}
