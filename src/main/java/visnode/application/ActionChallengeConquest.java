package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import visnode.challenge.ChallengeConquestPanel;
import visnode.challenge.MissionUserPanel;
import visnode.gui.IconFactory;

/**
 * The challenge user conquests action
 */
public class ActionChallengeConquest extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionChallengeConquest() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:dollar"));
        Messages.get().message("challenge.conquest").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChallengeConquestPanel.showDialog();
    }

}
