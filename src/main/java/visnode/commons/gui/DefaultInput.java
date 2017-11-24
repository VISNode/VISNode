/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import io.reactivex.Observable;

/**
 * Default input
 * 
 * @param <V>
 * @param <T>
 */
public interface DefaultInput<V, T extends DefaultInput> extends DefaultComponent<T>, GenericInput<V, T> {

    @Override
    public default V value() {
        return getDefaultInputSupport().value();
    }

    @Override
    public default T value(V value) {
        return (T) getDefaultInputSupport().value(value);
    }

    @Override
    public default Observable<V> valueObservable() {
        return getDefaultInputSupport().valueObservable();
    }

    @Override
    public default T value(Observable<V> value) {
        return (T) getDefaultInputSupport().value(value);
    }
    
    @Override
    public default DefaultComponentSupport<T> getDefaultComponentSupport() {
        return (DefaultComponentSupport<T>) getDefaultInputSupport();
    }
    
    /**
     * Returns the input support
     *
     * @return DefaultInputSupport
     */
    public DefaultInputSupport<V, T> getDefaultInputSupport();
    
}
