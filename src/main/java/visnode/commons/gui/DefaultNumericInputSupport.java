/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import javax.swing.JFormattedTextField;

/**
 * Default numeric input support
 * 
 * @param <V>
 * @param <T>
 */
public class DefaultNumericInputSupport<V, T extends DefaultInput> extends DefaultInputSupport<V, T> implements GenericInput<V, DefaultInput> {
    
    public DefaultNumericInputSupport(DefaultInput component) {
        super(component);
    }

    @Override
    public V value() {
        return (V) swingComponent().getValue();
    }

    @Override
    public T value(V value) {
        swingComponent().setValue(value);
        return (T) component;
    }

    @Override
    public Observable<V> valueObservable() {
        Subject<V> subject = BehaviorSubject.createDefault(value());
        swingComponent().addPropertyChangeListener("value", (evt) -> {
            subject.onNext((V)evt.getNewValue());
        });
        return subject;
    }

    @Override
    public T value(Observable<V> value) {
        value.subscribe((v) -> value(v));
        return (T) component;
    }

    @Override
    protected JFormattedTextField swingComponent() {
        return (JFormattedTextField) super.swingComponent();
    }
    
}
