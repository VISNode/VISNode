package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import visnode.challenge.ChallengeRankingPane;
import visnode.user.UserController;

/**
 * The challenge ranking
 */
public class ActionChallengeRanking extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionChallengeRanking() {
        super();
        putValue(NAME, "Ranking");
        UserController.get().isLogged().subscribe((has) -> {
            setEnabled(has);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChallengeRankingPane.showDialog();

    }

}
