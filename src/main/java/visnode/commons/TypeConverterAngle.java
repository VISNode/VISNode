package visnode.commons;

/**
 * Angle converter
 */
public class TypeConverterAngle implements TypeConverterExecutor {

    @Override
    public <D> D convert(Object value, Class<D> destinyType) {
        return (D) new Angle((Integer) value);
    }

    @Override
    public <D> boolean can(Class sourceType, Class<D> destinyType) {
        return (sourceType.equals(Integer.class) || sourceType.equals(int.class)) && 
                destinyType.equals(Angle.class);
    }

}
