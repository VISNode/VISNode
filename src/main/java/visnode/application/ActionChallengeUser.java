package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import visnode.challenge.ChallengeUserPanel;
import visnode.gui.IconFactory;

/**
 * The challenge user action
 */
public class ActionChallengeUser extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionChallengeUser() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:user"));
        Messages.get().message("user").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChallengeUserPanel.showDialog();
    }

}
