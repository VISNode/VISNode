package visnode.commons.reflection;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Reflection representation of a class
 *
 * @author Jouwee
 */
public class RClass {

    /** Class */
    private final Class clazz;

    /**
     * Private class
     *
     * @param clazz
     */
    private RClass(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * Returns a class
     *
     * @param object
     * @return RClass
     */
    public static RClass get(Object object) {
        return get(object.getClass());
    }

    /**
     * Returns a class
     *
     * @param clazz
     * @return RClass
     */
    public static RClass get(Class clazz) {
        return new RClass(clazz);
    }

    /**
     * Returns the properties
     *
     * @return {@code List<RProperty>}
     * @throws ReflectionException
     */
    public List<RProperty> getProperties() throws ReflectionException {
        List<RProperty> ret = new ArrayList<>();
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
                if (pd.getReadMethod() != null) {
                    ret.add(new RProperty(pd));
                }
            }
        } catch (IntrospectionException | IllegalArgumentException e) {
            throw new ReflectionException(e);
        }
        return ret;
    }

}
