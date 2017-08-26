package visnode.application.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import visnode.application.ExceptionHandler;

/**
 * Event dispatcher
 */
public class EventDispatcher implements GenericEventDispatcher {

    /** Singleton instance */
    private static EventDispatcher instance;
    /** Listeners */
    private final ModelsListeners listeners;

    /**
     * Creates a new EventDispatcher
     */
    public EventDispatcher() {
        this.listeners = new ModelsListeners();
    }
    
    /**
     * Returns the instance of the dispatcher
     * 
     * @return static
     */
    public static EventDispatcher get() {
        if (instance == null) {
            instance = new EventDispatcher();
        }
        return instance;
    }
    
    
    @Override
    public <T extends EventObject> void addEventListener(Class<T> eventType, Model model, EventListener<T> listener) {
        listeners.add(model, eventType, listener);
    }

    @Override
    public <T extends EventObject> void removeEventListener(Class<T> eventType, Model model, EventListener<T> listener) {
        
    }

    @Override
    public <T extends EventObject> void fireEvent(Model model, T event) {
        try {
            for (EventListener listener : listeners.get(model, event.getClass())) {
                listener.observed(event);
            }
        } catch(Exception e) {
            ExceptionHandler.get().handle(e);
        }
    }
    
    /**
     * Listeners by model
     */
    private class ModelsListeners {
        
        /** Listeners by model */
        private final Map<Integer, ListenerList> listeners;

        /**
         * Models by listener
         */
        public ModelsListeners() {
            this.listeners = new HashMap<>();
        }
        
        /**
         * Adds an event listener
         * 
         * @param model
         * @param listener 
         */
        public void add(Model model, Class<? extends EventObject> eventType, EventListener listener) {
            getToAdd(model).add(model, eventType, listener);
        }
        
        /**
         * Returns the list for addition
         * 
         * @param model
         * @return ListenerList
         */
        public ListenerList getToAdd(Model model) {
            Integer key = key(model);
            if (!listeners.containsKey(key)) {
                ListenerList list = new ListenerList();
                listeners.put(key, list);
                return list;
            } else {
                return listeners.get(key);
            }
        }
        
        /**
         * Returns a list of listeners for the model
         * 
         * @param type
         * @return 
         */
        public List<EventListener> get(Model model, Class<? extends EventObject> type) {
            Integer key = key(model);
            if (listeners.containsKey(key)) {
                return listeners.get(key).get(type);
            }
            return new ArrayList<>();
        }
        
        /**
         * Returns the map Key
         * 
         * @param model
         * @return Integer
         */
        public Integer key(Model model) {
            return System.identityHashCode(model);
        }
        
    }
    
    /** 
     * Listener list
     */
    private class ListenerList {
        
        /** Listener list */
        private final List<ListenerBean> list;

        /**
         * Listener list
         */
        public ListenerList() {
            this.list = new ArrayList<>();
        }
        
        /**
         * Ads a listener
         * 
         * @param model
         * @param listener 
         */
        public void add(Model model, Class eventType, EventListener listener) {
            list.add(new ListenerBean(model, listener, eventType));
        }
        
        /**
         * Returns the listeners
         * 
         * @param type
         * @return List<EventListener>
         */
        public List<EventListener> get(Class<? extends EventObject> type) {
            List<EventListener> ret = new ArrayList<>();
            for (ListenerBean bean : new ArrayList<>(list)) {
                if (type.equals(bean.type)) {
                    ret.add(bean.listener);
                }
            }
            return ret;
        }
        
    }
    
    /**
     * ListenerBean
     */
    private class ListenerBean {
        
        /** Model */
        private final Model model;
        /** Listener */
        private final EventListener listener;
        /** Type */
        private final Class<? extends EventObject> type;

        /**
         * Crates a new ListenerBean
         * 
         * @param model
         * @param listener 
         */
        public ListenerBean(Model model, EventListener listener, Class<? extends EventObject> type) {
            this.model = model;
            this.listener = listener;
            this.type = type;
        }
        
        
    }

}
