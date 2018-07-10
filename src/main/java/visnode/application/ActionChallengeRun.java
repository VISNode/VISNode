package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import visnode.challenge.Challenge;
import visnode.challenge.ChallengeController;
import visnode.challenge.ChallengesPanel;
import visnode.commons.DynamicValue;
import visnode.gui.IconFactory;

/**
 * The challenge run action
 */
public class ActionChallengeRun extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionChallengeRun() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:play"));
        Messages.get().message("challenge.run").subscribe((msg) -> {
            putValue(NAME, msg);
        });
        ChallengeController.get().hasChallenge().subscribe((has) -> {
            setEnabled(has);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DynamicValue ouput = VISNode.get().getModel().getNetwork().getOutput();
        if (ouput == null) {
            return;
        }
        ChallengeController.get().comparate().thenAccept((accepted) -> {
            SwingUtilities.invokeLater(() -> {
                if (accepted) {
                    JOptionPane.showMessageDialog(null, "The output is correct! :)");
                    Challenge challenge = ChallengeController.get().getChallenge();
                    ChallengeController.get().end();                   
                    VISNode.get().getController().createNew();
                    if (challenge.getMission().getLevel() > challenge.getLevel()) {
                        ChallengesPanel.showDialog(challenge.getMission());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The output is incorrect! :(");
                }
            });
        });
    }

}
