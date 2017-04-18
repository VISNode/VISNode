
package visnode.application.mvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Event of an element added to a list
 */
public class ListAddEvent extends EventObject {

    /** List name */
    private final String listName;
    /** List */
    private final List list;
    /** Index of element added */
    private final int addedIndex;

    /**
     * Creates a new event
     * 
     * @param listName
     * @param list
     */
    public ListAddEvent(String listName, List list) {
        this(listName, list, list.size() - 1);
    }

    /**
     * Creates a new event
     * 
     * @param listName
     * @param list
     * @param addedIndex 
     */
    public ListAddEvent(String listName, List list, int addedIndex) {
        this.listName = listName;
        this.list = new ArrayList<>(list);
        this.addedIndex = addedIndex;
    }

    /**
     * Returns the list name
     * 
     * @return String
     */
    public String getListName() {
        return listName;
    }

    /**
     * Returns the list
     * 
     * @return List
     */
    public List getList() {
        return list;
    }

    /**
     * Returns the index added
     * 
     * @return int
     */
    public int getAddedIndex() {
        return addedIndex;
    }
    
    /**
     * Returns the item added
     * 
     * @return Object
     */
    public Object getAddedItem() {
        return list.get(addedIndex);
    }
    
}
