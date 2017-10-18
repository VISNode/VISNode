package visnode.commons;

/**
 * Threshold converter
 */
public class TypeConverterThreshold implements TypeConverterExecutor {

    @Override
    public <D> D convert(Object value, Class<D> destinyType) {
        return (D) new Threshold((Integer) value);
    }

    @Override
    public <D> boolean can(Class sourceType, Class<D> destinyType) {
        return (sourceType.equals(Integer.class) || sourceType.equals(int.class)) && 
                destinyType.equals(Threshold.class);
    }

}
