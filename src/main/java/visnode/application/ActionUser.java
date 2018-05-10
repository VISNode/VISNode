package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.SMALL_ICON;
import visnode.gui.IconFactory;
import visnode.user.UserController;

/**
 * User action
 */
public class ActionUser extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionUser() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:user"));
        UserController.get().isLogged().subscribe((has) -> {
            setEnabled(has);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    }

}
