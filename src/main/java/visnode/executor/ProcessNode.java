package visnode.executor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import visnode.commons.Input;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import visnode.commons.Output;
import visnode.pdi.Process;
import visnode.pdi.ImageProcess;

/**
 * Process node representation
 */
public class ProcessNode implements Node, AttacherNode {

    /** Process class */
    private final Class<ImageProcess> process;
    /** Process input */
    private final List<NodeParameter> processInput;
    /** Process output */
    private final Map<String, Method> processOutput;
    /** Node connector */
    private final NodeConnector connector;
    /** Node parameter */
    private final Map<String, Object> parameters;
    /** Property change support */
    private final PropertyChangeSupport propertyChangeSupport;

    /**
     * Creates a new process node
     *
     * @param process
     */
    public ProcessNode(Class process) {
        this.process = process;
        this.processInput = buildProcessInput();
        this.processOutput = buildProcessOutput();
        this.connector = new NodeConnector();
        this.parameters = new HashMap<>();
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Builds inputs attributes
     *
     * @return List
     */
    private List<NodeParameter> buildProcessInput() {
        Constructor constructor = process.getConstructors()[0];
        return Arrays.stream(constructor.getParameters()).filter((p) -> {
            return p.isAnnotationPresent(Input.class);
        }).map((p) -> {
            return new NodeParameter(p.getAnnotation(Input.class).value(), p.getType());
        }).collect(Collectors.toList());
    }

    /**
     * Builds outputs attributes
     *
     * @return Map
     */
    private Map buildProcessOutput() {
        Map outputs = new HashMap<>();
        Method[] methods = process.getMethods();
        Arrays.stream(methods).filter((method) -> {
            return method.getAnnotation(Output.class) != null;
        }).forEach((method) -> {
            String name = method.getAnnotation(Output.class).value();
            outputs.put(name, method);
        });
        return outputs;
    }

    @Override
    public Object getAttribute(String attribute) {
        if (parameters.containsKey(attribute)) {
            return parameters.get(attribute);
        }
        if (processOutput.containsKey(attribute)) {
            try {
                Process prc = buildProcess();
                prc.process();
                return processOutput.get(attribute).invoke(prc);
            } catch (IllegalArgumentException | ReflectiveOperationException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    @Override
    public void addParameter(String parameter, Object value) {
        Object oldValue = parameters.get(parameter);
        parameters.put(parameter, value);
        propertyChangeSupport.firePropertyChange(parameter, oldValue, value);
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
        NodeConnection connection = connector.getConnection(parameter);
        return connection.getNode().getAttribute(connection.getAttribute());
    }

    /**
     * Builds a new process
     *
     * @return ImageProcess
     */
    private Process buildProcess() {
        try {
            Constructor constructor = process.getConstructors()[0];
            List<Object> list = new ArrayList<>();
            processInput.forEach((input) -> {
                list.add(getProperty(input.getName()));
            });
            return (Process) constructor.newInstance(list.toArray());
        } catch (IllegalArgumentException | ReflectiveOperationException ex) {
            throw new RuntimeException("Process build fail", ex);
        }
    }

    @Override
    public void addConnection(String attribute, Node node, String attributeNode) {
        connector.addConnection(attribute, node, attributeNode);
    }

    @Override
    public List<NodeParameter> getInputParameters() {
        return Collections.unmodifiableList(processInput);
    }

    @Override
    public List<NodeParameter> getOutputParameters() {
        return processOutput.entrySet().stream().map((p) -> {
            return new NodeParameter(p.getKey(), p.getValue().getReturnType());
        }).collect(Collectors.toList());
    }

    @Override
    public NodeConnector getConnector() {
        return connector;
    }
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }


    @Override
    public String getName() {
        return process.getSimpleName();
    }
   
}
