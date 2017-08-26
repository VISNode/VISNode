package visnode.commons;

/**
 * Dynamic node value
 */
public class DynamicNodeValue {

    private final String value;

    public DynamicNodeValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public boolean hasValue() {
        return value!=null && !value.isEmpty();
    }

}
