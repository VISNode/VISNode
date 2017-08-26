package visnode.application.mvc;

/**
 * Generic event dispatcher
 */
public interface GenericEventDispatcher {
    
    /**
     * Adds an event listener to the model
     * 
     * @param <T>
     * @param model
     * @param listener 
     */
    public <T extends EventObject> void addEventListener(Class<T> eventType, Model model, EventListener<T> listener);
    
    /**
     * Removes an event listener to the model
     * 
     * @param <T>
     * @param model
     * @param listener 
     */
    public <T extends EventObject> void removeEventListener(Class<T> eventType, Model model, EventListener<T> listener);
    
    /**
     * Fires an event
     * 
     * @param <T>
     * @param model
     * @param event
     */
    public <T extends EventObject> void fireEvent(Model model, T event);
    
}
