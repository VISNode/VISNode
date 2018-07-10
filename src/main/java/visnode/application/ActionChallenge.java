package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import visnode.challenge.MissionListPanel;
import visnode.user.LoginPanel;
import visnode.user.UserController;

/**
 * The challenge action
 */
public class ActionChallenge extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionChallenge() {
        super();
        Messages.get().message("challenge.start").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserController.get().isLogged().first(false).subscribe((has) -> {
            if (has) {
                MissionListPanel.showDialog();
                return;
            }
            LoginPanel.showDialog();
            UserController.get().isLogged().subscribe((logged) -> {
                if (logged) {
                    MissionListPanel.showDialog();
                }
            }).dispose();
        });
    }

}
