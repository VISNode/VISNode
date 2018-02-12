package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import visnode.challenge.ChallengeProblemPanel;
import visnode.challenge.ChallengeScope;
import visnode.gui.IconFactory;

/**
 * The challenge problem action
 */
public class ActionChallengeProblem extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionChallengeProblem() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:question"));
        Messages.get().message("challenge.problem").subscribe((msg) -> {
            putValue(NAME, msg);
        });
        ChallengeScope.get().hasChallenge().subscribe((has) -> {
            setEnabled(has);
        });        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChallengeProblemPanel.showDialog();
    }
}
