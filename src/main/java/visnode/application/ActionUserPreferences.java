package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import visnode.gui.UserPreferencesPanel;

/**
 * Action for opening the user preferences
 */
public class ActionUserPreferences extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionUserPreferences() {
        super();
        Messages.get().message("preferences").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        UserPreferencesPanel.showDialog();
    }

}
