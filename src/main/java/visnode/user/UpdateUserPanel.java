package visnode.user;

import com.github.rxsling.Buttons;
import com.github.rxsling.Labels;
import com.github.rxsling.Panel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.commons.ImageScale;
import visnode.commons.swing.FileChooserFactory;
import visnode.commons.swing.WindowFactory;
import visnode.gui.UIHelper;
import visnode.repository.RepositoryException;
import visnode.repository.UserRepository;

/**
 * Update user panel
 */
public class UpdateUserPanel extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 150;
    /** Password */
    private JPasswordField password;
    /** Institution */
    private JTextField institution;
    /** User model */
    private final User model;

    /**
     * Creates a new login panel
     */
    private UpdateUserPanel() {
        this.model = UserController.get().getUser();
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        WindowFactory.modal().title("Update user").create((container) -> {
            container.add(new UpdateUserPanel());
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 320));
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
        panel.add(Buttons.create("Update").onClick((ev) -> {
            try {
                model.setInstitution(institution.getText());
                model.setPassword(new String(password.getPassword()));
                UserRepository.get().update(model);
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
        JPanel form = new JPanel();
        form.setLayout(new GridLayout(0, 1));
        form.add(Labels.create().text(Messages.get().message("password")));
        form.add(buildPassword());
        form.add(Labels.create().text(Messages.get().message("institution")));
        form.add(buildInstitution());
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(form);
        container.add(buildImage(), BorderLayout.SOUTH);
        return container;
    }

    /**
     * Builds the password field
     *
     * @return JComponent
     */
    private JComponent buildPassword() {
        password = new JPasswordField();
        password.setText(model.getPassword());
        return password;
    }

    /**
     * Builds the institution field
     *
     * @return JComponent
     */
    private JComponent buildInstitution() {
        institution = new JTextField();
        institution.setText(model.getInstitution());
        return institution;
    }

    /**
     * Returns the user image icon
     *
     * @return ImageIcon
     */
    private ImageIcon getIcon() {
        BufferedImage image = ImageScale.scale(model.getImageBuffered(), THUMBNAIL_SIZE);
        return new ImageIcon(image);
    }

    /**
     * Builds the image field
     *
     * @return JComponent
     */
    private JComponent buildImage() {
        JLabel label = new JLabel(getIcon());
        label.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JPanel panel = new JPanel();
        panel.add(label);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileChooserFactory.openImage().accept((file) -> {
                    Base64Image base64Image = new Base64Image();
                    model.setImage(base64Image.toBase64(file));
                    label.setIcon(getIcon());
                });
            }
        }
        );
        return panel;
    }
}
