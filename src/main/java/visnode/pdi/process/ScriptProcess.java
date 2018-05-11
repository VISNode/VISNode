package visnode.pdi.process;

import visnode.application.ScriptRunner;
import visnode.commons.DynamicValue;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.commons.ScriptValue;

/**
 * Script process
 */
public class ScriptProcess implements visnode.pdi.Process {

    /** Input value */
    private final DynamicValue input;
    /** Script runner */
    private final ScriptRunner scriptRunner;
    /** Output value */
    private DynamicValue value;

    /**
     * Creates a new script process
     *
     * @param input
     * @param script
     */
    public ScriptProcess(@Input("input") DynamicValue input, @Input("script") ScriptValue script) {
        this.input = input;
        this.scriptRunner = new ScriptRunner(script);
    }

    @Override
    public void process() {
        
        
        value = new DynamicValue(scriptRunner.invokeFunction("process", input));
    }

    @Output("value")
    public DynamicValue getOutput() {
        return value;
    }

}
