package visnode.executor;

import io.reactivex.Observable;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.swing.event.EventListenerList;
import visnode.commons.DynamicValue;

/**
 * Output node representation
 */
public class OutputNode implements Node, AttacherNode {

    /** Node connector */
    private final NodeConnector connector;
    /** Property change support */
    private final PropertyChangeSupport propertyChangeSupport;
    /** Listeners list */
    private final EventListenerList listenerList;
    /** Value */
    private DynamicValue value;

    /**
     * Creates a new output node
     */
    public OutputNode() {
        this.connector = new NodeConnector(this);
        propertyChangeSupport = new PropertyChangeSupport(this);
        listenerList = new EventListenerList();
        value = null;
    }

    @Override
    public Object getInput(String attribute) {
        if (attribute.equals("value")) {
            return value;
        }
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public void setInput(String attribute, Object value) {
        if (attribute.equals("value")) {
            this.value = value instanceof DynamicValue
                    ? (DynamicValue) value
                    : new DynamicValue(value);
            propertyChangeSupport.firePropertyChange(attribute, null, value);
            return;
        }
        throw new InvalidAttributeException(attribute);
    }

    /**
     * Returns the output image
     *
     * @return DynamicValue
     */
    public DynamicValue getValue() {
        return value;
    }

    @Override
    public Observable getOutput(String attribute) {
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public void setOutput(String attribute, Object value) {
        throw new InvalidAttributeException(attribute);
    }

    @Override
    public void addConnection(final String attribute, Node node, String attributeNode) {
        connector.addConnection(attribute, node, attributeNode);
    }

    @Override
    public void removeConnection(String attribute) {
        connector.removeConnection(attribute);
    }

    @Override
    public List<NodeParameter> getInputParameters() {
        List<NodeParameter> list = new ArrayList<>();
        list.add(new NodeParameter("value", DynamicValue.class));
        return list;
    }

    @Override
    public List<NodeParameter> getOutputParameters() {
        return new ArrayList<>();
    }

    @Override
    public NodeConnector getConnector() {
        return connector;
    }

    @Override
    public void addInputChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void addOutputChangeListener(PropertyChangeListener listener) {
    }

    @Override
    public void removeOutputChangeListener(PropertyChangeListener listener) {
    }

    @Override
    public String getName() {
        return "Output";
    }

    @Override
    public void addConnectionChangeListener(ConnectionChangeListener listener) {
        listenerList.add(ConnectionChangeListener.class, listener);
    }

    @Override
    public void dispose() {
    }

    public CompletableFuture<DynamicValue> execute() {
        CompletableFuture future = new CompletableFuture();
        NodeConnection nodeConnection = getConnector().getConnection("value");
        ProcessNode node = (ProcessNode) nodeConnection.getLeftNode();
        process(node).thenAcceptAsync((i) -> {
            node.getOutput(nodeConnection.getLeftAttribute()).subscribe((it) -> {
                future.complete(getValue());
            });
        });
        return future;
    }

    private CompletableFuture process(ProcessNode processNode) {
        CompletableFuture future = new CompletableFuture();
        NodeConnector nodeConnector = processNode.getConnector();
        if (nodeConnector.hasConnection()) {
            List<CompletableFuture> list = new ArrayList<>();
            nodeConnector.getConnections().values().forEach((it) -> {
                list.add(process((ProcessNode) it.getLeftNode()));
            });
            CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()])).thenAccept((i) -> {
                processNode.process((call) -> {
                    nodeConnector.getConnections().values().forEach((it) -> {
                        processNode.setInput(it.getRightAttribute(), it.getLeftNode().getOutput(it.getLeftAttribute()).blockingFirst());
                    });
                    future.complete(null);
                });
            });
        } else {
            processNode.getOutput("image").subscribe((it) -> {
                future.complete(null);
            });
        }
        return future;
    }
}
