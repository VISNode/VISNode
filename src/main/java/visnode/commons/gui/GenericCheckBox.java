/*
 * TesteInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import io.reactivex.Observable;

/**
 * Generic CheckBox
 * 
 * @param <T>
 */
public interface GenericCheckBox<T extends GenericCheckBox> extends Component<T> {

    /**
     * Sets if the component is selected
     *
     * @param selected
     * @return T
     */
    public T selected(boolean selected);

    /**
     * Sets if the component is selected
     *
     * @param selected
     * @return T
     */
    public T selected(Observable<Boolean> selected);

    /**
     * Returns if the component is selected
     *
     * @return boolean
     */
    public boolean selected();

    /**
     * Returns an observable of the component selected status
     *
     * @return Observable
     */
    public Observable<Boolean> selectedObservable();

}
