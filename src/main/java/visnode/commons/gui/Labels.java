/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

/**
 * Label factory
 */
public class Labels {
    
    /**
     * Creates a new label
     * 
     * @param text
     * @return Label
     */
    public static Label create(String text) {
        return create().text(text);
    }
    
    /**
     * Creates a new label
     * 
     * @return Label
     */
    public static Label create() {
        return new Label();
    }
    
}
