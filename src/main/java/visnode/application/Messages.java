package visnode.application;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Locale messages
 */
public class Messages {

    /** Locale messages instance */
    private static Messages instance;
    /** Resource */
    private ResourceBundle messages;

    private final Map<String, BehaviorSubject> subjects;

    /**
     * Creates a new locale messages
     */
    private Messages() {
        this.subjects = new HashMap<>();

    }
    
    /**
     * Returns the resource bundle
     * 
     * @return ResourceBundle
     */
    private ResourceBundle getResource() {
        if (messages == null) {
            VISNode.get().getModel().getUserPreferences().getLocaleSubject().subscribe((locale) -> {
                messages = ResourceBundle.getBundle("MessagesBundle", locale);
                subjects.forEach((key, value) -> {
                    value.onNext(singleMessage(key));
                });
            });
        }
        return messages;
    }

    /**
     * Returns the message
     *
     * @param key
     * @return
     */
    public String singleMessage(String key) {
        return getResource().getString(key);
    }

    /**
     * Returns a message observable
     *
     * @param key
     * @return {@code Observable<String>}
     */
    public Observable<String> message(String key) {
        BehaviorSubject subject = subjects.get(key);
        if (subject != null) {
            return subject;
        }
        subject = BehaviorSubject.createDefault(singleMessage(key));
        subjects.put(key, subject);
        return subject;
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
