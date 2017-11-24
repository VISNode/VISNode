/*
 * PrototipoInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import io.reactivex.Observable;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Interface for a generic component
 *
 * @param <T>
 */
public interface Component<T extends Component> {

    /**
     * Sets the components ID
     *
     * @param id
     * @return T
     */
    public T id(String id);

    /**
     * Returns the components ID
     *
     * @return id
     */
    public String id();

    /**
     * Sets if the component is enabled
     *
     * @param enabled
     * @return T
     */
    public T enabled(boolean enabled);

    /**
     * Sets if the component is enabled
     *
     * @param enabled
     * @return T
     */
    public T enabled(Observable<Boolean> enabled);

    /**
     * Returns if the component is enabled
     *
     * @return boolean
     */
    public boolean enabled();

    /**
     * Returns an observable of the component enabled status
     *
     * @return Observable
     */
    public Observable<Boolean> enabledObservable();
    

    /**
     * Sets if the component is focusable
     *
     * @param focusable
     * @return T
     */
    public T focusable(boolean focusable);

    /**
     * Sets if the component is focusable
     *
     * @param focusable
     * @return T
     */
    public T focusable(Observable<Boolean> focusable);

    /**
     * Returns if the component is focusable
     *
     * @return boolean
     */
    public boolean focusable();

    /**
     * Returns an observable of the component focusable status
     *
     * @return Observable
     */
    public Observable<Boolean> focusableObservable();

    /**
     * Sets the background of the component
     *
     * @param background
     * @return T
     */
    public T background(Color background);

    /**
     * Sets the background of the component
     *
     * @param background
     * @return T
     */
    public T background(Observable<Color> background);

    /**
     * Returns the background of the component
     *
     * @return boolean
     */
    public Color background();

    /**
     * Returns an observable of the background of the component
     *
     * @return Observable
     */
    public Observable<Color> backgroundObservable();
        
    /**
     * Consumes on click events
     * 
     * @param consumer 
     * @return T
     */
    public T onClick(Consumer<MouseEvent> consumer);
    
    /**
     * Casts this component as another type
     * 
     * @param <K>
     * @param type
     * @return K
     */
    public <K> K as(Class<K> type);
    
    /**
     * Style the component using CSS
     * 
     * @param style
     * @return T
     */
    public T style(String style);

}
