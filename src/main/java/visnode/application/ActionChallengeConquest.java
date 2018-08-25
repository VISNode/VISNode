package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import visnode.challenge.ChallengeConquestPanel;
import visnode.gui.IconFactory;
import visnode.user.UserController;

/**
 * The challenge user conquests action
 */
public class ActionChallengeConquest extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionChallengeConquest() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:dollar"));
        Messages.get().message("challenge.conquest").subscribe((msg) -> {
            putValue(NAME, msg);
        });
        UserController.get().isLogged().subscribe((has) -> {
            setEnabled(has);
        });        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChallengeConquestPanel.showDialog();
    }

}
