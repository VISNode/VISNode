package visnode.commons;

/**
 * Dynamic value converter
 */
public class TypeConverterDynamicValue implements TypeConverterExecutor {

    @Override
    public <D> D convert(Object value, Class<D> destinyType) {
        return (D) new DynamicValue<>(value);
    }

    @Override
    public <D> boolean can(Class sourceType, Class<D> destinyType) {
        return destinyType.equals(DynamicValue.class);
    }

}
