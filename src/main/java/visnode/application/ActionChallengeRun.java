package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JOptionPane;
import org.paim.commons.Image;
import visnode.challenge.Challenge;
import visnode.challenge.ChallengeComparator;
import visnode.challenge.ChallengeScope;
import visnode.gui.IconFactory;

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
        putValue(SMALL_ICON, IconFactory.get().create("fa:play"));
        Messages.get().message("challenge.run").subscribe((msg) -> {
            putValue(NAME, msg);
        });
        ChallengeScope.get().hasChallenge().subscribe((has) -> {
            setEnabled(has);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Image ouput = VISNode.get().getModel().getNetwork().getOutput();
        if (ouput == null) {
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
