package visnode.gui;

import visnode.application.ProcessMetadata;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.ProcessNode;

/**
 * Process editor documentation factory
 */
public class ScriptValueEditorDocumentationFactory {

    /**
     * Create a process editor documentation
     * 
     * @param node
     * @return ScriptValueEditorDocumentation
     */
    public static ScriptValueEditorDocumentation create(Node node) {
        if (node instanceof EditNodeDecorator &&
                ((EditNodeDecorator) node).getDecorated() instanceof ProcessNode) {
            ProcessNode processNode = (ProcessNode) ((EditNodeDecorator) node).getDecorated();
            return new ScriptValueEditorDocumentationScript(
                    processNode,
                    ProcessMetadata.fromClass(processNode.getProcessType())
            );
        }
        return new ScriptValueEditorDocumentation() {
        };
    }

}
