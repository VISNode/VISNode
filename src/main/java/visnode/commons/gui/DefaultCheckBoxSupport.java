/*
 * TesteInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import javax.swing.JCheckBox;

/**
 * Default checkbox support
 *
 * @param <T>
 */
public class DefaultCheckBoxSupport<T extends DefaultCheckBox> extends DefaultComponentSupport<DefaultCheckBox>
        implements GenericCheckBox<DefaultCheckBox> {

    /**
     * Creates a new Support
     * 
     * @param component 
     */
    public DefaultCheckBoxSupport(T component) {
        super(component);
    }
    
    @Override
    public T selected(boolean selected) {
        swingComponent().isSelected();
        return (T) component;
    }

    @Override
    public T selected(Observable selected) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean selected() {
        return swingComponent().isSelected();
    }

    @Override
    public Observable<Boolean> selectedObservable() {
        Subject<Boolean> subject = BehaviorSubject.createDefault(selected());
        swingComponent().addPropertyChangeListener("selected", (evt) -> {
            subject.onNext((Boolean)evt.getNewValue());
        });
        return subject;
    }

    @Override
    protected JCheckBox swingComponent() {
        return (JCheckBox) super.swingComponent();
    }

}
