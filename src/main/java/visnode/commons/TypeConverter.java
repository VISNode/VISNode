package visnode.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Type converter
 */
public class TypeConverter {

    /** Converters */
    private final List<TypeConverterExecutor> converters;

    public TypeConverter() {
        this.converters = new ArrayList<>();
        this.converters.add(new TypeConverterThreshold());
        this.converters.add(new TypeConverterAngle());
        this.converters.add(new TypeConverterGeneric());
        this.converters.add(new TypeConverterGenericNumber());
        this.converters.add(new TypeConverterDynamicValue());
    }

    /**
     * Converts a {@code value} to a value of type {@code <D>} (Destiny)
     *
     * @param <D>
     * @param value
     * @param destinyType
     * @return D
     */
    public <D> D convert(Object value, Class<D> destinyType) {
        if (value == null) {
            return null;
        }
        return convert(value, value.getClass(), destinyType);
    }

    /**
     * Converts a {@code value} to a value of type {@code <D>} (Destiny)
     *
     * @param <D>
     * @param value
     * @param sourceType
     * @param destinyType
     * @return D
     */
    public <D> D convert(Object value, Class sourceType, Class<D> destinyType) {
        try {
            if (value == null) {
                return null;
            }
            if (destinyType.isAssignableFrom(sourceType)) {
                return (D) value;
            }
            if (sourceType.equals(DynamicValue.class)) {
                Object dynamicValue = ((DynamicValue) value).get();
                return converters.stream().
                        filter((converter) -> converter.can(dynamicValue.getClass(), destinyType)).
                        findFirst().get().convert(dynamicValue, destinyType);
            }
            return converters.stream().
                    filter((converter) -> converter.can(sourceType, destinyType)).
                    findFirst().get().convert(value, destinyType);
        } catch (NoSuchElementException e) {
            throw new UnsupportedOperationException("Unknown conversion from " + sourceType + " to " + destinyType, e);
        }
    }

    public boolean isValidConversion(Class sourceType, Class destinyType) {
        return converters.stream().anyMatch((converter) -> converter.can(sourceType, destinyType));
    }

}
