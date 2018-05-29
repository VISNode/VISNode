package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import visnode.gui.IconFactory;
import visnode.user.UserController;

/**
 * User action
 */
public class ActionUser extends JButton {

    public ActionUser() {
        super();
        setAction(new Action());
    }

    private class Action extends AbstractAction {

        /**
         * Creates a new action
         */
        public Action() {
            super();
            putValue(SMALL_ICON, IconFactory.get().create("fa:user"));
            UserController.get().isLogged().subscribe((has) -> {
                setEnabled(has);
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JPopupMenu popup = new JPopupMenu();
            JMenuItem menuItem = new JMenuItem("Logout", IconFactory.get().create("fa:sign-out"));
            menuItem.addActionListener((ev) -> {
                UserController.get().logout();
            });
            popup.add(menuItem);
            popup.show(ActionUser.this, 0, 0);
        }

    }

}
