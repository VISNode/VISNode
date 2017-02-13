package visnode.application.mvc;

/**
 * Event listener for the model
 * 
 * @param <T>
 */
public interface EventListener<T extends EventObject> {

    /**
     * And event was observed 
     * 
     * @param event 
     */
    public void observed(T event);
    
}
