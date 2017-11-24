/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Default label support
 * 
 * @param <T>
 */
public class DefaultLabelSupport<T extends DefaultLabel> extends DefaultComponentSupport<T>
        implements GenericLabel<T> {

    /**
     * Creates a new Support
     * 
     * @param component 
     */
    public DefaultLabelSupport(T component) {
        super(component);
    }
    
    @Override
    public T text(String text) {
        swingComponent().setText(text);
        return (T) component;
    }

    @Override
    public T text(Observable<String> observable) {
        observable.subscribe((text) -> {
            text(text);
        });
        return (T) component;
    }

    @Override
    public String text() {
        return swingComponent().getText();
    }

    @Override
    public Observable<String> textObservable() {
        Subject<String> subject = BehaviorSubject.createDefault(text());
        swingComponent().addPropertyChangeListener("text", (evt) -> {
            subject.onNext((String)evt.getNewValue());
        });
        return subject;
    }
    
    @Override
    public T icon(Icon icon) {
        swingComponent().setIcon(icon);
        return component;
    }

    @Override
    public T icon(Observable<Icon> observable) {
        observable.subscribe((icon) -> {
            icon(icon);
        });
        return component;
    }

    @Override
    public Icon icon() {
        return swingComponent().getIcon();
    }

    @Override
    public Observable<Icon> iconObservable() {
        Subject<Icon> subject = BehaviorSubject.createDefault(icon());
        swingComponent().addPropertyChangeListener("icon", (evt) -> {
            subject.onNext((Icon)evt.getNewValue());
        });
        return subject;
    }

    @Override
    protected JLabel swingComponent() {
        return (JLabel) super.swingComponent();
    }

}
