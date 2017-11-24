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
 * Default button support
 * 
 * @param <T>
 */
public interface DefaultButton<T extends DefaultButton> extends DefaultComponent<T>, GenericButton<T> {

    @Override
    public default T text(String selected) {
        return getDefaultButtonSupport().text(selected);
    }

    @Override
    public default T text(Observable selected) {
        return (T) getDefaultButtonSupport().text(selected);
    }

    @Override
    public default String text() {
        return getDefaultButtonSupport().text();
    }

    @Override
    public default Observable textObservable() {
        return getDefaultButtonSupport().textObservable();
    }
    
    @Override
    public default T icon(Icon icon) {
        return getDefaultButtonSupport().icon(icon);
    }

    @Override
    public default T icon(Observable icon) {
        return (T) getDefaultButtonSupport().icon(icon);
    }

    @Override
    public default Icon icon() {
        return getDefaultButtonSupport().icon();
    }

    @Override
    public default Observable iconObservable() {
        return getDefaultButtonSupport().iconObservable();
    }

    @Override
    public default DefaultComponentSupport<T> getDefaultComponentSupport() {
        return getDefaultButtonSupport();
    }
    
    /**
     * Returns the default button support
     * 
     * @return DefaultButtonSupport
     */
    public DefaultButtonSupport<T> getDefaultButtonSupport();
    
}
