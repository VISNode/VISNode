/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import io.reactivex.Observable;

/**
 * Generic Input
 * 
 * @param <V>
 * @param <T>
 */
public interface GenericInput<V, T extends GenericInput> extends Component<T> {
    
    /**
     * Returns the input value
     * 
     * @return V
     */
    public V value();
    
    /**
     * Sets the input value
     * 
     * @param value
     * @return T
     */
    public T value(V value);
    
    /**
     * Returns an observable for the value
     * 
     * @return Observable
     */
    public Observable<V> valueObservable();
    
    /**
     * Sets an observable as the value
     * 
     * @param value
     * @return T
     */
    public T value(Observable<V> value);
    
}
