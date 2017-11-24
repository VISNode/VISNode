/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import javax.swing.JButton;

/**
 * Simple button
 */
public class Button extends JButton implements DefaultButton<Button> {

    /** Default button support */
    private final DefaultButtonSupport<Button> buttonSupport;

    /**
     * Creates a new button
     */
    public Button() {
        this.buttonSupport = new DefaultButtonSupport<>(this);
    }
    
    @Override
    public DefaultButtonSupport<Button> getDefaultButtonSupport() {
        return buttonSupport;
    }
    
}
