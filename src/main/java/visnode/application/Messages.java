package visnode.application;

import java.util.ResourceBundle;

/**
 * Locale messages
 */
public class Messages {

    /** Locale messages instance */
    private static Messages instance;
    /** Resource */
    private final ResourceBundle messages;
    
    /**
     * Creates a new locale messages
     */
    private Messages() {
        this.messages = ResourceBundle.getBundle("MessagesBundle", Configuration.get().getLocale());
    }
        
    /**
     * Returns the message
     * 
     * @param key
     * @return 
     */
    public String message(String key) {
        return messages.getString(key);
    } 
    
    /**
     * Returns the locale message instance
     * 
     * @return Messages
     */
    public static Messages get() {
        if (instance == null) {
            instance = new Messages();
        }
        return instance;
    }
}
