package visnode.user;

import com.github.rxsling.Buttons;
import com.github.rxsling.Labels;
import com.github.rxsling.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.commons.swing.WindowFactory;

/**
 * Login panel
 */
public class LoginPanel extends JPanel {

    /** User */
    private JTextField user;

    /**
     * Creates a new login panel
     */
    private LoginPanel() {
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        WindowFactory.modal().title("Login").create((container) -> {
            container.add(new LoginPanel());
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
        panel.add(Buttons.create("Login").onClick((ev) -> {
            UserController.get().login(user.getText());
            SwingUtilities.getWindowAncestor(this).dispose();
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
        panel.add(buildLanguage());
        return panel;
    }

    /**
     * Builds the language field
     *
     * @return JComponent
     */
    private JComponent buildLanguage() {
        user = new JTextField();
        return user;
    }
}
