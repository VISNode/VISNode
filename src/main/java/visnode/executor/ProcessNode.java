package visnode.executor;

import com.google.common.base.Objects;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.event.EventListenerList;
import org.apache.commons.lang3.math.NumberUtils;
import visnode.application.ExceptionHandler;
import visnode.application.ProcessMetadata;
import visnode.application.VISNode;
import visnode.commons.Output;
import visnode.commons.TypeConverter;
import visnode.pdi.Process;

/**
 * Process node representation
 */
public class ProcessNode implements Node, AttacherNode {

    /** Execution pool */
    private static ExecutorService pool;
    /** Process class */
    private final Class<Process> processType;
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
    private Process lastProcess;
    /** If the process has been invalidated */
    private boolean invalidated;
    /** Process meta-data */
    private ProcessMetadata metadata;
    /** Lock used for the output */
    private final Object outputLock = new Object();
    
    /**
     * Creates a new process node
     *
     * @param process
     */
    public ProcessNode(Class process) {
        this.input = new HashMap<>();
        this.processType = process;
        this.processInput = buildProcessInput();
        this.processOutput = buildProcessOutput();
        this.connector = new NodeConnector(this);
        this.inputChangeSupport = new PropertyChangeSupport(this);
        this.outputChangeSupport = new PropertyChangeSupport(this);
        this.listenerList = new EventListenerList();
        this.invalidated = true;
    }

    /**
     * Builds inputs attributes
     *
     * @return List
     */
    private List<NodeParameter> buildProcessInput() {
        ProcessMetadata meta = ProcessMetadata.fromClass((Class<Process>) processType);
        Constructor constructor = processType.getConstructors()[0];
        return Arrays.stream(constructor.getParameters()).filter((p) -> {
            return p.isAnnotationPresent(Input.class);
        }).map((p) -> {
            NodeParameter param = new NodeParameter(p.getAnnotation(Input.class).value(), p.getType());
            dafaultInput(meta, param);
            return param;
        }).collect(Collectors.toList());
    }

    /**
     * Sets the default input value
     *
     * @param meta
     * @param param
     */
    private void dafaultInput(ProcessMetadata meta, NodeParameter param) {
        if (meta.getDefault(param.getName()) != null) {
            TypeConverter converter = new TypeConverter();
            Object value;
            try {
                value = NumberUtils.createNumber(meta.getDefault(param.getName()));
            } catch (NumberFormatException ex) {
                value = meta.getDefault(param.getName());
            }
            this.input.put(param.getName(), converter.convert(value, param.getType()));
        }
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
        if (!Objects.equal(value, oldValue)) {
            input.put(attribute, value);
            inputChangeSupport.firePropertyChange(attribute, oldValue, value);
            invalidated = true;
        }
    }

    @Override
    public Observable getOutput(String attribute) {
        BehaviorSubject subject = BehaviorSubject.create();
        if (invalidated) {
            process((p) -> {
                Object value = getOutputValue(p, attribute);
                if (value != null) {
                    if (value instanceof Observable) {
                        ((Observable)value).subscribe((v) -> {
                            synchronized (outputLock) {
                                subject.onNext(v);
                            }
                        });
                    } else {
                        synchronized (outputLock) {
                            subject.onNext(value);
                        }
                    }
                }
            });
            return subject;
        }
        Object value = getOutputValue(lastProcess, attribute);
        if (value != null) {
            if (value instanceof Observable) {
                ((Observable)value).subscribe((v) -> {
                    synchronized (outputLock) {
                        subject.onNext(v);
                    }
                });
            } else {
                synchronized (outputLock) {
                    subject.onNext(value);
                }
            }
        }
        return subject;
    }

    /**
     * Returns the output value
     * 
     * @param attribute
     * @return Object
     */
    private Object getOutputValue(Process process, String attribute) {
        try {
            return processOutput.get(attribute).invoke(process);
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
        }
        return null;
    }

    @Override
    public void setOutput(String attribute, Object value) {
    }

    /**
     * Runs the process
     *
     * @param callable
     */
    public void process(Consumer<Process> callable) {
        Process process = buildProcess();
        getPool().submit(() -> {
            try {
                process.process();
                invalidated = false;
                for (Map.Entry<String, Method> entry : processOutput.entrySet()) {
                    Object output = getOutputValue(process, entry.getKey());
                    if (output instanceof Observable) {
                        ((Observable) output).subscribe((value) -> {
                            outputChangeSupport.firePropertyChange(entry.getKey(), null, value);
                        });
                    } else {
                        outputChangeSupport.firePropertyChange(entry.getKey(), null, output);
                    }
                }
                lastProcess = process;
                callable.accept(process);
            } catch (Exception ex) {
                ExceptionHandler.get().handle(ex);
            }
        });
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
    
    /**
     * Returns the thread pool for executing processes
     * 
     * @return ExecutorService
     */
    private static ExecutorService getPool() {
        if (pool == null) {
            pool = Executors.newWorkStealingPool();
        }
        return pool;
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
            Class returnType = p.getValue().getReturnType();
            Output meta = p.getValue().getAnnotation(Output.class);
            if (meta.observableOf() != Void.class) {
                returnType = meta.observableOf();
            }
            return new NodeParameter(p.getKey(), returnType);
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
        System.out.println("shit");
        VISNode.get().getModel().getUserPreferences().getLocaleSubject().subscribe((locale) -> {
            metadata = ProcessMetadata.fromClass(processType, locale);
        });
        return metadata.getName();
    }

    @Override
    public void addConnectionChangeListener(ConnectionChangeListener listener) {
        listenerList.add(ConnectionChangeListener.class, listener);
    }

    public Class getProcessType() {
        return processType;
    }
}
