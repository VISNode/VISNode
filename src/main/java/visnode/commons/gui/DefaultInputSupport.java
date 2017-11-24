/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

/**
 * Default input support
 * 
 * @param <V>
 * @param <T>
 */
public abstract class DefaultInputSupport<V, T extends DefaultInput> extends DefaultComponentSupport<DefaultInput>
        implements GenericInput<V, DefaultInput> {
    
    public DefaultInputSupport(DefaultInput component) {
        super(component);
    }
    
}
