package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.SwingUtilities;
import visnode.challenge.Mission;
import visnode.challenge.ChallengeController;
import visnode.challenge.ChallengeSuccessMessagePanel;
import visnode.challenge.MissionErrorMessagePanel;
import visnode.challenge.MissionSuccessMessagePanel;
import visnode.challenge.MissionsPanel;
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
        ChallengeController.get().hasMission().subscribe((has) -> {
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
                    Mission mission = ChallengeController.get().getMission();
                    if (mission.getChallenge().getLevel() == mission.getLevel()) {
                        ChallengeSuccessMessagePanel.showDialog(mission);
                    } else {
                        MissionSuccessMessagePanel.showDialog(mission);
                    }
                    ChallengeController.get().end();                   
                    VISNode.get().getController().createNew();
                    if (mission.getChallenge().getLevel() > mission.getLevel()) {
                        MissionsPanel.showDialog(mission.getChallenge());
                    }
                } else {
                    MissionErrorMessagePanel.showDialog();
                }
            });
        });
    }

}
