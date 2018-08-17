package visnode.gui;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Script value editor log
 */
public class ScriptValueEditorLog {
    
    /** Instance */
    private static ScriptValueEditorLog intance;
    
    private final Subject<String> observable;

    public ScriptValueEditorLog() {
        this.observable = BehaviorSubject.create();
    }
    
    /**
     * Returns the observable
     * 
     * @return 
     */
    public Observable<String> observable()  {
        return observable;
    }
    
    /**
     * Returns the next value
     * 
     * @param log 
     */
    public void next(String log) {
        observable.onNext(log);
    }
    
    
    /**
     * Returns the instance
     * 
     * @return StringValueEditorLog
     */
    public static ScriptValueEditorLog get() {
        if (intance == null) {
            intance = new ScriptValueEditorLog();
        }
        return intance;
    }
    
}
