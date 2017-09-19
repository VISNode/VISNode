package visnode.commons;

/**
 * Dynamic node value
 */
public class ScriptValue {

    private final String value;

    public ScriptValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public boolean hasValue() {
        return value!=null && !value.isEmpty();
    }

}
