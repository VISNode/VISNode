package visnode.executor;

import visnode.commons.Input;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import visnode.pdi.ImageProcess;

/**
 * Process node representation
 */
public class ProcessNode implements Node, AttacherNode {

    /** Process class */
    private final Class<ImageProcess> process;
    /** Node connector */
    private final NodeConnector connector;
    /** Node parameter */
    private final Map<String, Object> parameters;

    /**
     * Creates a new process node
     * 
     * @param process 
     */
    public ProcessNode(Class process) {
        this.process = process;
        this.connector = new NodeConnector();
        this.parameters = new HashMap<>();
    }

    @Override
    public Object getAttribute(String attribute) {
        ImageProcess prc = buildProcess();
        prc.process();
        return prc.getOutput();
    }

    /**
     * Adds a new parameter
     * 
     * @param parameter
     * @param value 
     */
    public void addParameter(String parameter, Object value) {
        parameters.put(parameter, value);
    }

    /**
     * Returns a node property
     * 
     * @param parameter
     * @return Object
     */
    private Object getProperty(String parameter) {
        if (parameters.containsKey(parameter)) {
            return parameters.get(parameter);
        }
        return connector.getConnection(parameter).getNode().getAttribute(parameter);
    }

    /**
     * Builds a new process
     * 
     * @return ImageProcess
     */
    private ImageProcess buildProcess() {
        try {
            Constructor constructor = process.getConstructors()[0];
            List<Object> list = new ArrayList<>();
            for (Annotation[] t : constructor.getParameterAnnotations()) {
                list.add(getProperty(((Input) t[0]).value()));
            }
            return (ImageProcess) constructor.newInstance(list.toArray());
        } catch (Exception ex) {
            throw new RuntimeException("Process build fail", ex);
        }
    }

    @Override
    public void addConnection(String attribute, Node node, String attributeNode) {
        connector.addConnection(attribute, node, attributeNode);
    }

}
