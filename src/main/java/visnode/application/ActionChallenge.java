package visnode.application;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import visnode.challenge.Challenge;
import visnode.challenge.ChallengeRepository;
import visnode.challenge.ChallengeScope;

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
        Challenge challenge = repository.getChallenges().get(0);
        ChallengeScope.get().start(challenge);
        VISNode.get().getController().createNew();
        VISNode.get().getModel().getNetwork().setInput(new File(challenge.getInput()));
    }

}
