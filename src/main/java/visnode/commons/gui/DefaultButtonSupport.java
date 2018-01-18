/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.AbstractButton;
import javax.swing.Icon;

/**
 * Default button support
 * 
 * @param <T>
 */
public class DefaultButtonSupport<T extends DefaultButton> extends DefaultComponentSupport<T> implements GenericButton<T> {
    
    /**
     * Creates a new Default button support
     * 
     * @param component 
     */
    public DefaultButtonSupport(T component) {
        super(component);
    }
    
    @Override
    public T text(String text) {
        swingComponent().setText(text);
        return component;
    }

    @Override
    public T text(Observable<String> observable) {
        observable.subscribe((text) -> {
            text(text);
        });
        return component;
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
    public T onClick(Consumer<MouseEvent> consumer) {
        swingComponent().addActionListener((evt) -> {
            consumer.accept(new MouseEvent(swingComponent(), evt.getID(), evt.getWhen(), evt.getModifiers(), 0, 0, 1, false));
        });
        return component;
    }

    @Override
    protected AbstractButton swingComponent() {
        return (AbstractButton) super.swingComponent();
    }
    
}
