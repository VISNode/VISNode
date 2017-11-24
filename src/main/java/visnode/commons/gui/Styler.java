/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

/**
 * Interface for applying styles into components
 * 
 * @param <T>
 */
public interface Styler<T extends Component> {
    
    /**
     * Applies a style to the component
     * 
     * @param style 
     */
    public void apply(String style); 
    
    /**
     * Returns the component to be styled
     * 
     * @return T
     */
    public T getComponent();
    
}
