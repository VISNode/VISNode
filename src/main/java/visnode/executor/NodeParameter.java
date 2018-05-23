package visnode.executor;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Node parameter
 */
public class NodeParameter {
    
    /** Name */
    private final String name;
    /** Type */
    private final Class type;
    /** Annotations */
    private final Annotation[] annotations;

    public NodeParameter(String name, Class type) {
        this(name, type, new Annotation[0]);
    }

    public NodeParameter(String name, Class type, Annotation[] annotations) {
        this.name = name;
        this.type = type;
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }
    
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(type)) {
                return (T) annotation;
            }
        }
        return null;
    }
    
    public boolean hasAnnotation(Class<? extends Annotation> type) {
        return getAnnotation(type) != null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodeParameter other = (NodeParameter) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }
 
    
}
