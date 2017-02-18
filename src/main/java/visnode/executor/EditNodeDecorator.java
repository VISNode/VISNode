package visnode.executor;

import java.awt.Point;

/**
 * Node decorator that add editing capabilities
 */
public class EditNodeDecorator implements Node {

    /** Decrated node */
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
    public Object getAttribute(String attribute) {
        return decorated.getAttribute(attribute);
    }

}
