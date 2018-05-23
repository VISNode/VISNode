package visnode.commons;

import java.io.File;
import org.paim.commons.BinaryImage;
import org.paim.commons.Image;

/**
 * Generic type converter
 */
public class TypeConverterGenericNumber implements TypeConverterExecutor {

    @Override
    public <D> D convert(Object value, Class<D> destinyType) {
        Number v = (Number) value;
        if (destinyType.equals(Integer.class) || destinyType.equals(int.class)) {
            return (D) new Integer(v.intValue());
        }
        if (destinyType.equals(Double.class) || destinyType.equals(double.class)) {
            return (D) new Double(v.doubleValue());
        }
        if (destinyType.equals(Float.class) || destinyType.equals(float.class)) {
            return (D) new Float(v.floatValue());
        }
        if (destinyType.equals(Long.class) || destinyType.equals(long.class)) {
            return (D) new Long(v.longValue());
        }
        return (D) v;
    }

    @Override
    public <D> boolean can(Class sourceType, Class<D> destinyType) {
        return (Number.class.isAssignableFrom(sourceType)) && (Number.class.isAssignableFrom(destinyType));
    }

}
