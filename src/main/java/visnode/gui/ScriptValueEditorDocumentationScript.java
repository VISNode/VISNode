package visnode.gui;

import java.util.HashMap;
import java.util.Map;
import org.paim.pdi.ObjectList;
import visnode.application.ProcessMetadata;
import visnode.commons.DynamicValue;
import visnode.commons.ScriptValue;
import visnode.executor.ProcessNode;

/**
 * Script editor documentations
 */
public class ScriptValueEditorDocumentationScript implements ScriptValueEditorDocumentation {

    /** Process node */
    private final ProcessNode node;
    /** Process meta data */
    private final ProcessMetadata processMetadata;

    public ScriptValueEditorDocumentationScript(ProcessNode node,
            ProcessMetadata processMetadata) {
        this.node = node;
        this.processMetadata = processMetadata;
    }

    @Override
    public String getSnippet() {
        return processMetadata.getSnippet() == null
                ? ScriptValueEditorDocumentation.super.getSnippet()
                : processMetadata.getSnippet();
    }

    @Override
    public Class getProcess() {
        return processMetadata.getProcess();
    }

    @Override
    public Map<String, String> getInputDocumentation() {
        Map map = new HashMap();
        node.getInputParameters().stream()
                .filter((input) -> !input.getType().equals(ScriptValue.class))
                .forEach((input) -> {
                    Class classType = input.getType();
                    String name = classType.getSimpleName();
                    String url = "";
                    if (classType.equals(DynamicValue.class)) {
                        Object valueInput = node.getInput(input.getName());
                        if (valueInput != null) {
                            if (valueInput instanceof DynamicValue) {
                                DynamicValue value = (DynamicValue) node.getInput(input.getName());
                                if (value != null) {
                                    classType = value.get().getClass();
                                }
                            } else {
                                classType = valueInput.getClass();
                            }
                            name = classType.getSimpleName();
                            if (classType.getPackage().toString().contains("org.paim")
                                    || classType.getPackage().toString().contains("visnode")) {
                                String nameMd = name;
                                if (classType.equals(ObjectList.class)) {
                                    nameMd = "ExtractedObject";
                                    name = nameMd + "[]";
                                }
                                url = "https://raw.githubusercontent.com/Jouwee/VISNode/master/src/main/resources/visnode/pdi/process/" + nameMd + ".md";
                            }
                        }
                    }
                    map.put(name, url);
                });
        return map;
    }

}
