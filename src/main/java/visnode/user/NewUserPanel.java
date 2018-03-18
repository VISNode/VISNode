package visnode.user;

import com.github.rxsling.Buttons;
import com.github.rxsling.Labels;
import com.github.rxsling.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.commons.swing.WindowFactory;
import visnode.repository.RepositoryException;
import visnode.repository.UserRepository;

/**
 * New user panel
 */
public class NewUserPanel extends JPanel {

    /** User */
    private JTextField user;
    /** Password */
    private JPasswordField password;

    /**
     * Creates a new login panel
     */
    private NewUserPanel() {
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        WindowFactory.modal().title("User").create((container) -> {
            container.add(new NewUserPanel());
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 150));
        add(buildPreferences(), BorderLayout.NORTH);
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Builds the button panel
     *
     * @return JComponent
     */
    private JComponent buildButtons() {
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.add(Buttons.create("Create").onClick((ev) -> {
            try {
                User model = new User(user.getText());
                model.setPassword(new String(password.getPassword()));
                UserRepository.get().create(model);
                SwingUtilities.getWindowAncestor(this).dispose();
            } catch (RepositoryException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }));
        return panel;
    }

    /**
     * Builds the preferences panel
     *
     * @return JComponent
     */
    private JComponent buildPreferences() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(Labels.create().text(Messages.get().message("user")));
        panel.add(buildUser());
        panel.add(Labels.create().text(Messages.get().message("password")));
        panel.add(buildPassword());
        return panel;
    }

    /**
     * Builds the user field
     *
     * @return JComponent
     */
    private JComponent buildUser() {
        user = new JTextField();
        return user;
    }
    
    /**
     * Builds the password field
     *
     * @return JComponent
     */
    private JComponent buildPassword() {
        password = new JPasswordField();
        return password;
    }
}
