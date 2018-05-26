package visnode.commons;

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
        return isNumber(sourceType) && isNumber(destinyType);
    }

    /**
     * Returns if a type is anumber
     * 
     * @param type
     * @return boolean
     */
    public boolean isNumber(Class type) {
        return Number.class.isAssignableFrom(type) || type.equals(int.class) || 
                type.equals(double.class) || type.equals(float.class) || 
                type.equals(long.class);
    }

}
