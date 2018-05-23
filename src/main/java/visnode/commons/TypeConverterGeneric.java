package visnode.commons;

import java.io.File;
import org.paim.commons.BinaryImage;
import org.paim.commons.Image;

/**
 * Generic type converter
 */
public class TypeConverterGeneric implements TypeConverterExecutor {

    @Override
    public <D> D convert(Object value, Class<D> destinyType) {
        return (D) value;
    }

    @Override
    public <D> boolean can(Class sourceType, Class<D> destinyType) {
        if (sourceType.equals(destinyType)) {
            return true;
        }
        if (BinaryImage.class.isAssignableFrom(sourceType) && destinyType.equals(Image.class)) {
            return true;
        }
        if (File.class.isAssignableFrom(sourceType) && File.class.isAssignableFrom(destinyType)) {
            return true;
        }
        return false;
    }

}
