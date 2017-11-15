package visnode.executor;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Node decorator that add editing capabilities
 */
public class EditNodeDecorator implements Node, AttacherNode {

    /** Decorated node */
    private final Node decorated;
    /** Node position */
    private Point position;
    
    /**
     * Creates a new node decorator for editing 
     * 
     * @param decorated 
     */
    public EditNodeDecorator(Node decorated) {
        this(decorated, new Point());
    }
    
    /**
     * Creates a new node decorator for editing 
     * 
     * @param decorated
     * @param position 
     */
    public EditNodeDecorator(Node decorated, Point position) {
        this.decorated = decorated;
        this.position = new Point(position);
    }

    /**
     * Returns the position of the node
     * 
     * @return Point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position of the node
     * 
     * @param position 
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Returns the decorated node
     * 
     * @return Node
     */
    public Node getDecorated() {
        return decorated;
    }

    @Override
    public Object getInput(String attribute) {
        return decorated.getInput(attribute);
    }

    @Override
    public void setInput(String attribute, Object value) {
        decorated.setInput(attribute, value);
    }

    @Override
    public Object getOutput(String attribute) {
        return decorated.getOutput(attribute);
    }

    @Override
    public void setOutput(String attribute, Object value) {
        decorated.setOutput(attribute, value);
    }
    
    @Override
    public List<NodeParameter> getInputParameters() {
        return decorated.getInputParameters();
    }

    @Override
    public List<NodeParameter> getOutputParameters() {
        return decorated.getOutputParameters();
    }

    @Override
    public NodeConnector getConnector() {
        return decorated.getConnector();
    }

    @Override
    public void addInputChangeListener(PropertyChangeListener listener) {
        decorated.addInputChangeListener(listener);
    }

    @Override
    public void addOutputChangeListener(PropertyChangeListener listener) {
        decorated.addOutputChangeListener(listener);
    }

    @Override
    public void removeOutputChangeListener(PropertyChangeListener listener) {
        decorated.removeOutputChangeListener(listener);
    }

    @Override
    public String getName() {
        return decorated.getName();
    }

    @Override
    public void addConnection(String attribute, Node node, String attributeNode) {
        if (!(decorated instanceof AttacherNode)) {
            return;
        }
        ((AttacherNode)decorated).addConnection(attribute, node, attributeNode);
    }

    @Override
    public void removeConnection(String attribute) {
        if (!(decorated instanceof AttacherNode)) {
            return;
        }
        ((AttacherNode)decorated).removeConnection(attribute);
    }
    
    @Override
    public void addConnectionChangeListener(ConnectionChangeListener listener) {
        if (!(decorated instanceof AttacherNode)) {
            return;
        }
        ((AttacherNode)decorated).addConnectionChangeListener(listener);
    }

}
