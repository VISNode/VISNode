
package visnode.gui;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Selection
 */
public class Selection<T> extends ArrayList<T> {
    
    /**
     * Creates an empty selection
     */
    public Selection() {
    }
    
    /**
     * Creates a new selection based on a object list
     * 
     * @param collection 
     */
    public Selection(Collection<T> collection) {
        super(collection);
    }
    
    /**
     * Sets the selection
     * 
     * @param selection 
     */
    void set(T selection) {
        clear();
        add(selection);
    }

    /**
     * Sets the selection
     * 
     * @param selection 
     */
    void set(Collection<T> selection) {
        clear();
        addAll(selection);
    }

    /**
     * Returns a copy of the selection
     * 
     * @return {@code Selection<T>}
     */
    public Selection<T> copy() {
        Selection<T> copy = new Selection<>();
        copy.set(this);
        return copy;
    }
    
}
