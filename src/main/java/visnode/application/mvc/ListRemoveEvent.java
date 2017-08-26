
package visnode.application.mvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Event of an element removed from a list
 */
public class ListRemoveEvent extends EventObject {

    /** List name */
    private final String listName;
    /** List */
    private final List list;
    /** Index of element removed */
    private final int removedIndex;
    /** Removed bean */
    private final Object removedBean;

    /**
     * Creates a new event
     * 
     * @param listName
     * @param list
     * @param removedBean
     */
    public ListRemoveEvent(String listName, List list, Object removedBean) {
        this(listName, list, list.size() - 1, removedBean);
    }

    /**
     * Creates a new event
     * 
     * @param listName
     * @param list
     * @param removedIndex 
     * @param removedBean
     */
    public ListRemoveEvent(String listName, List list, int removedIndex, Object removedBean) {
        this.listName = listName;
        this.list = new ArrayList<>(list);
        this.removedIndex = removedIndex;
        this.removedBean = removedBean;
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
     * Returns the index removed
     * 
     * @return int
     */
    public int getRemovedIndex() {
        return removedIndex;
    }

    /**
     * Returns the removed bean
     * 
     * @return Object
     */
    public Object getRemovedBean() {
        return removedBean;
    }
    
}
