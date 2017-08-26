package visnode.application.mvc;

/**
 * Generic model
 */
public interface Model {

    /**
     * Adds an event listener to the model
     * 
     * @param <T>
     * @param listener 
     */
    public default <T extends EventObject> void addEventListener(Class<T> eventType, EventListener<T> listener) {
        EventDispatcher.get().addEventListener(eventType, this, listener);
    }
        
    /**
     * Removes an event listener to the model
     * 
     * @param <T>
     * @param listener 
     */
    public default <T extends EventObject> void removeEventListener(Class<T> eventType, EventListener<T> listener) {
        EventDispatcher.get().removeEventListener(eventType, this, listener);
    }
    
    /**
     * Fires an event
     * 
     * @param <T>
     * @param event
     */
    public default <T extends EventObject> void fireEvent(T event) {
        EventDispatcher.get().fireEvent(this, event);
    }
    
    /**
     * Adds an event listener to the model to every of its children
     * 
     * @param <T>
     * @param listener 
     */
    public default <T extends EventObject> void addChildEventListener(Class<T> eventType, EventListener<T> listener) {
        EventMulticaster.get(this).addEventListener(eventType, listener);
    }
        
    /**
     * Removes an event listener to the model and to every of its children
     * 
     * @param <T>
     * @param listener 
     */
    public default <T extends EventObject> void removeChildEventListener(EventListener<T> listener) {
        EventMulticaster.get(this).removeEventListener(listener);
    }
    
}
