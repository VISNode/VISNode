/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import javax.swing.JToggleButton;

/**
 * Toggle button
 */
public class ToggleButton extends JToggleButton implements DefaultButton<ToggleButton> {

    /** Default button support */
    private final DefaultButtonSupport<ToggleButton> buttonSupport;

    /**
     * Creates a new button
     */
    public ToggleButton() {
        this.buttonSupport = new DefaultButtonSupport<>(this);
    }
    
    @Override
    public DefaultButtonSupport<ToggleButton> getDefaultButtonSupport() {
        return buttonSupport;
    }
    
}
