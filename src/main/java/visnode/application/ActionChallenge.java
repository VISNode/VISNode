package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import visnode.challenge.ChallengeListPanel;

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
        ChallengeListPanel.showDialog();
    }

}
