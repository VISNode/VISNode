/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import io.reactivex.Observable;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.Icon;

/**
 * Generic button
 * 
 * @param <T>
 */
public interface GenericButton<T extends GenericButton> extends Component<T> {
    
    /**
     * Sets the label text
     *
     * @param selected
     * @return T
     */
    public T text(String selected);

    /**
     * Sets the label text
     *
     * @param text
     * @return T
     */
    public T text(Observable<String> text);

    /**
     * Returns the label text
     *
     * @return String
     */
    public String text();

    /**
     * Returns an observable of the label text
     *
     * @return Observable
     */
    public Observable<String> textObservable();
    
    /**
     * Sets the icon
     *
     * @param selected
     * @return T
     */
    public T icon(Icon selected);

    /**
     * Sets the icon
     *
     * @param icon
     * @return T
     */
    public T icon(Observable<Icon> icon);

    /**
     * Returns the icon
     *
     * @return String
     */
    public Icon icon();

    /**
     * Returns an observable of the icon
     *
     * @return Observable
     */
    public Observable<Icon> iconObservable();
    
}
