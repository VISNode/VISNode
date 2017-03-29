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
    private final Class<ImageProcess> processType;
    /** Process input */
    private final List<NodeParameter> processInput;
    /** Process output */
    private final Map<String, Method> processOutput;
    /** Node connector */
    private final NodeConnector connector;
    /** Node input */
    private final Map<String, Object> input;
    /** Input change support */
    private final PropertyChangeSupport inputChangeSupport;
    /** Output change support */
    private final PropertyChangeSupport outputChangeSupport;
    /** The f instance to run */
    private Process process;    
    /** If the process has been invalidated */
    private boolean invalidated;

    /**
     * Creates a new process node
     *
     * @param process
     */
    public ProcessNode(Class process) {
        this.processType = process;
        this.processInput = buildProcessInput();
        this.processOutput = buildProcessOutput();
        this.connector = new NodeConnector(this);
        this.input = new HashMap<>();
        this.inputChangeSupport = new PropertyChangeSupport(this);
        this.outputChangeSupport = new PropertyChangeSupport(this);
        invalidated = true;
    }

    /**
     * Builds inputs attributes
     *
     * @return List
     */
    private List<NodeParameter> buildProcessInput() {
        Constructor constructor = processType.getConstructors()[0];
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
        Method[] methods = processType.getMethods();
        Arrays.stream(methods).filter((method) -> {
            return method.getAnnotation(Output.class) != null;
        }).forEach((method) -> {
            String name = method.getAnnotation(Output.class).value();
            outputs.put(name, method);
        });
        return outputs;
    }
    
    @Override
    public Object getInput(String attribute) {
        return input.get(attribute);
    }

    @Override
    public void setInput(String attribute, Object value) {
        Object oldValue = input.get(attribute);
        input.put(attribute, value);
        inputChangeSupport.firePropertyChange(attribute, oldValue, value);
        invalidated = true;
    }

    @Override
    public Object getOutput(String attribute) {
        if (invalidated) {
            process();
        }
        try {
            return processOutput.get(attribute).invoke(process);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setOutput(String attribute, Object value) {
    }

    /**
     * Runs the process
     */
    public void process() {
        process = buildProcess();
        process.process();
        invalidated = false;
        for (Map.Entry<String, Method> entry : processOutput.entrySet()) {
            outputChangeSupport.firePropertyChange(entry.getKey(), null, getOutput(entry.getKey()));
        }
    }

    /**
     * Builds a new process
     *
     * @return ImageProcess
     */
    private Process buildProcess() {
        try {
            Constructor constructor = processType.getConstructors()[0];
            List<Object> list = new ArrayList<>();
            processInput.forEach((input) -> {
                list.add(getInput(input.getName()));
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
    public void addInputChangeListener(PropertyChangeListener listener) {
        inputChangeSupport.addPropertyChangeListener(listener);
    }
    
    @Override
    public void addOutputChangeListener(PropertyChangeListener listener) {
        outputChangeSupport.addPropertyChangeListener(listener);
    }


    @Override
    public String getName() {
        return processType.getSimpleName();
    }

}
