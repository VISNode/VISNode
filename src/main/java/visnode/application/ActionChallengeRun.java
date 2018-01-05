package visnode.application;

import static java.awt.PageAttributes.MediaType.C;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.JOptionPane;
import org.paim.commons.Image;
import visnode.challenge.Challenge;
import visnode.challenge.ChallengeComparator;
import visnode.challenge.ChallengeScope;

/**
 * The challenge run action
 */
public class ActionChallengeRun extends AbstractAction {

    /** The challenge run */
    private final ChallengeComparator comparator;
    
    /**
     * Creates a new action
     */
    public ActionChallengeRun() {
        super();
        this.comparator = new ChallengeComparator();
        putValue(NAME, "run");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Image ouput = VISNode.get().getModel().getNetwork().getOutput();
        if (ouput == null || !ChallengeScope.get().hadChallenge()) {
            return;
        }
        
        Challenge challenge = ChallengeScope.get().getChallenge();
        if (comparator.comparate(challenge, ouput)) {
            JOptionPane.showMessageDialog(null, "The output is correct! :)");
        } else {
            JOptionPane.showMessageDialog(null, "The output is incorrect! :(");
        }
    }

}
