/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

/**
 * Button factory
 */
public class Buttons {
    
    /**
     * Creates a simple button
     * 
     * @return Button
     */
    public static Button create() {
        return new Button();
    }

    /**
     * Creates a simple button
     * 
     * @param text
     * @return Button
     */
    public static Button create(String text) {
        return create().text(text);
    }
    
    /**
     * Creates an undecorated button
     * 
     * @return Button
     */
    public static Button undecorated() {
        return create();
    }
    
}
