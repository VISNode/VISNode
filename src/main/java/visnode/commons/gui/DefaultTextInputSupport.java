package visnode.commons.gui;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import javax.swing.JTextField;

/**
 * Default text input support
 * 
 * @param <T>
 */
public class DefaultTextInputSupport<T extends DefaultInput> extends DefaultInputSupport<String, T> implements GenericInput<String, DefaultInput> {
    
    public DefaultTextInputSupport(DefaultInput component) {
        super(component);
    }

    @Override
    public String value() {
        return (String) swingComponent().getText();
    }

    @Override
    public T value(String value) {
        swingComponent().setText(value);
        return (T) component;
    }

    @Override
    public Observable<String> valueObservable() {
        Subject<String> subject = BehaviorSubject.createDefault(value());
        swingComponent().addPropertyChangeListener("value", (evt) -> {
            subject.onNext((String)evt.getNewValue());
        });
        return subject;
    }

    @Override
    public T value(Observable<String> value) {
        value.subscribe((v) -> value(v));
        return (T) component;
    }

    @Override
    protected JTextField swingComponent() {
        return (JTextField) super.swingComponent();
    }
    
}
