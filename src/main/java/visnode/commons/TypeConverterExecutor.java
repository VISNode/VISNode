package visnode.commons;

/**
 * Type converter executor
 */
public interface TypeConverterExecutor {

    /**
     * Executes the conversion
     * 
     * @param <D>
     * @param value
     * @param destinyType
     * @return D
     */
    public <D> D convert(Object value, Class<D> destinyType);
    
    /**
     * Returns true if can convert
     * 
     * @param <D>
     * @param sourceType
     * @param destinyType
     * @return 
     */
    public <D> boolean can(Class sourceType, Class<D> destinyType);
    
}
