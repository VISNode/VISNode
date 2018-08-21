package visnode.application;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import visnode.commons.ImageScale;
import visnode.gui.IconFactory;
import visnode.gui.UIHelper;
import visnode.user.UpdateUserPanel;
import visnode.user.User;
import visnode.user.UserController;

/**
 * User action
 */
public class ActionUser extends JButton {
    
    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 150;
    
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
            JMenuItem logout = new JMenuItem("Logout", IconFactory.get().create("fa:sign-out"));
            logout.addActionListener((ev) -> {
                UserController.get().logout();
            });
            JMenuItem edit = new JMenuItem("Edit", IconFactory.get().create("fa:pencil"));
            edit.addActionListener((ev) -> {
                UpdateUserPanel.showDialog();
            });
            User user = UserController.get().getUser();
            BufferedImage image = ImageScale.scale(user.getImageBuffered(), THUMBNAIL_SIZE);
            JLabel label = new JLabel(new ImageIcon(image));
            label.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    popup.setVisible(false);
                    UpdateUserPanel.showDialog();
                }
            });
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            JPanel panel = new JPanel();
            panel.add(label); 
            panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIHelper.getColor("Node.border")));
            popup.add(panel);
            popup.add(edit);
            popup.add(logout);
            popup.show(ActionUser.this, 0, 0);
        }

    }

}
