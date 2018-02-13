package visnode.commons;

/**
 * Dynamic node value
 */
public class ScriptValue {

    /** Script code */
    private final String value;

    /**
     * Creates a new script value
     *
     * @param value
     */
    public ScriptValue(String value) {
        this.value = value;
    }

    /**
     * Returns the script value
     *
     * @return String
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns true if has value
     *
     * @return boolean
     */
    public boolean hasValue() {
        return value != null && !value.isEmpty();
    }

}
