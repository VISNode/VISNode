package visnode.application;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import visnode.commons.ScriptValue;

/**
 * Script runner
 */
public class ScriptRunner {

    /** Script */
    private final ScriptValue script;
    /** Script invocable */
    private Invocable inv;    
    
    public ScriptRunner(ScriptValue script) {
        this.script = script;
        buildInvocable();
    }

     /**
     * Returns true if there is a script
     *
     * @return boolean
     */
    private boolean hasScript() {
        return script != null && script.hasValue();
    }
    
    
    /**
     * Builds the script invocable
     */
    private void buildInvocable() {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        try {
            if (hasScript()) {
                engine.eval(script.getValue());
                inv = (Invocable) engine;
            }
        } catch (ScriptException e) {
            ExceptionHandler.get().handle(e);
        }
    }
    
    
    public Object invokeFunction(String function, Object... values) {
        if (hasScript() && inv != null) {
            try {
                return inv.invokeFunction(function, values);
            } catch (NoSuchMethodException | ScriptException e) {
                ExceptionHandler.get().handle(e);
            }
        }
        return null;
    }
    
    
}
