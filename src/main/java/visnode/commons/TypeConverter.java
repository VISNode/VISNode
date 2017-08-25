package visnode.commons;

/**
 * Type converter
 */
public class TypeConverter {

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
        if (value == null) {
            return null;
        }
        if (sourceType.equals(destinyType)) {
            return (D) value;
        }
        if (sourceType.equals(Integer.class) && destinyType.equals(Threshold.class)) {
            return (D) new Threshold((Integer) value);
        }
        if (sourceType.equals(Integer.class) && destinyType.equals(int.class)) {
            return (D) value;
        }
        if (sourceType.equals(Double.class) && destinyType.equals(double.class)) {
            return (D) value;
        }
        throw new UnsupportedOperationException("Unknown conversion from " + sourceType + " to " + destinyType);
    }

}
