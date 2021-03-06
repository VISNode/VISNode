package visnode.challenge;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import visnode.application.ActionChallenge;
import visnode.application.ActionChallengeConquest;
import visnode.application.ActionChallengeProblem;
import visnode.application.ActionChallengeRanking;
import visnode.application.ActionChallengeRun;
import visnode.application.ActionChallengeUser;
import visnode.user.UserController;

/**
 * Returns the challenge menu item
 */
public class ChallengeMenu {

    /**
     * Returns the challenge menu
     *
     * @return {@code List<Action>}
     */
    public List<Action> getMenu() {
        List<Action> actions = new ArrayList<>();
        actions.add(new ActionChallenge());
        actions.add(new ActionChallengeRun());
        actions.add(new ActionChallengeProblem());
        actions.add(new ActionChallengeRanking());
        actions.add(new ActionChallengeConquest());
        if (UserController.get().getUser().isUserEditor()) {
            actions.add(new ActionChallengeUser());
        }
        return actions;
    }

}
