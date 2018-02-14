/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JOptionPane;
import visnode.challenge.Challenge;
import visnode.challenge.ChallengeController;
import visnode.commons.DynamicValue;
import visnode.gui.IconFactory;
import visnode.user.UserController;

/**
 * User action
 */
public class ActionUser extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionUser() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:user"));
        UserController.get().isLogged().subscribe((has) -> {
            setEnabled(has);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    }

}
