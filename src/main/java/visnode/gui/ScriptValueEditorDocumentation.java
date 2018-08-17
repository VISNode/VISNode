package visnode.gui;

import java.util.HashMap;
import java.util.Map;

/**
 * Script editor documentations
 */
public interface ScriptValueEditorDocumentation {

    /**
     * Returns the process snippet
     *
     * @return String
     */
    public default String getSnippet() {
        return "function process() {\n\n}";
    }

    /**
     * Returns the process class
     *
     * @return Class
     */
    public default Class getProcess() {
        return null;
    }

    /**
     * Returns the process input documentation
     *
     * @return {@code Map<String, String>
     */
    public default Map<String, String> getInputDocumentation() {
        return new HashMap();
    }

}
