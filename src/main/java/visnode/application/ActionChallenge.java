package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import visnode.challenge.ChallengeListPanel;
import visnode.challenge.ChallengeRepository;

/**
 * The challenge action
 */
public class ActionChallenge extends AbstractAction {

    /** The challenge repository */
    private final ChallengeRepository repository;
    
    /**
     * Creates a new action
     */
    public ActionChallenge() {
        super();
        this.repository = new ChallengeRepository();
        Messages.get().message("challenge").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChallengeListPanel.showDialog();
    }

}
