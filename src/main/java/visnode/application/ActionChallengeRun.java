package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JOptionPane;
import visnode.challenge.ChallengeController;
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
        if (ChallengeController.get().comparate(ouput)) {
            JOptionPane.showMessageDialog(null, "The output is correct! :)");
            VISNode.get().getController().createNew();
            ChallengeController.get().end();
        } else {
            JOptionPane.showMessageDialog(null, "The output is incorrect! :(");
        }
    }

}
