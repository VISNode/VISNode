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
import javax.swing.event.EventListenerList;
import org.paim.pdi.ImageProcess;
import visnode.application.ExceptionHandler;
import visnode.commons.Output;
import visnode.commons.TypeConverter;
import visnode.pdi.Process;

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
    /** Node input */
    private final Map<String, Object> input;
    /** Node connector */
    private final NodeConnector connector;
    /** Input change support */
    private final PropertyChangeSupport inputChangeSupport;
    /** Output change support */
    private final PropertyChangeSupport outputChangeSupport;
    /** Listeners list */
    private final EventListenerList listenerList;
    /** The instance to run */
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
        listenerList = new EventListenerList();
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
            ExceptionHandler.get().handle(e);
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
        Thread th = new Thread(() -> {
            process.process();
            invalidated = false;
            for (Map.Entry<String, Method> entry : processOutput.entrySet()) {
                outputChangeSupport.firePropertyChange(entry.getKey(), null, getOutput(entry.getKey()));
            }
        });
        th.setDaemon(true);
        th.start();
    }

    /**
     * Builds a new process
     *
     * @return ImageProcess
     */
    private Process buildProcess() {
        try {
            Constructor constructor = processType.getConstructors()[0];
            TypeConverter converter = new TypeConverter();
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < constructor.getParameterCount(); i++) {
                Object input = getInput(processInput.get(i).getName());
                list.add(converter.convert(input, constructor.getParameterTypes()[i]));
            }
            return (Process) constructor.newInstance(list.toArray());
        } catch (IllegalArgumentException | ReflectiveOperationException ex) {
            throw new RuntimeException("Process build fail", ex);
        }
    }

    @Override
    public void addConnection(String attribute, Node node, String attributeNode) {
        connector.addConnection(attribute, node, attributeNode);
        for (ConnectionChangeListener listener : listenerList.getListeners(ConnectionChangeListener.class)) {
            listener.connectionChanged(new ConnectionChangeEvent(new NodeParameter(attribute, null), ConnectionChangeEvent.EventType.CREATE, this));
        }
    }
    
    @Override
    public void removeConnection(String attribute) {
        connector.removeConnection(attribute);
        for (ConnectionChangeListener listener : listenerList.getListeners(ConnectionChangeListener.class)) {
            listener.connectionChanged(new ConnectionChangeEvent(new NodeParameter(attribute, null), ConnectionChangeEvent.EventType.REMOVE, this));
        }
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
    public void removeOutputChangeListener(PropertyChangeListener listener) {
        outputChangeSupport.removePropertyChangeListener(listener);
    }


    @Override
    public String getName() {
        return processType.getSimpleName();
    }

    @Override
    public void addConnectionChangeListener(ConnectionChangeListener listener) {
        listenerList.add(ConnectionChangeListener.class, listener);
    }
    
    public Class getProcessType() {
        return processType;
    }
}
