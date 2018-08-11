package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.commons.ImageScale;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.RepositoryException;
import visnode.repository.UserRepository;
import visnode.user.User;

/**
 * The challenge user panel
 */
public class MissionUserPanel extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 64;

    /**
     * Creates a new mission list panel
     */
    private MissionUserPanel() {
        super();
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        Messages.get().message("user").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new MissionUserPanel());
            }).setVisible(true);
        });
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildList());
    }

    /**
     * Creates the mission list
     *
     * @return JComponent
     */
    private JComponent buildList() {
        JComponent containerItems = new JPanel();
        containerItems.setLayout(new BorderLayout());
        containerItems.add(buildListComponent());
        return ScrollFactory.pane(containerItems).create();
    }

    /**
     * Returns the list component
     *
     * @return JComponent
     */
    private JComponent buildListComponent() {
        try {
            JPanel list = new JPanel();
            list.setLayout(new GridLayout(0, 1));
            UserRepository.get().getAll().forEach((user) -> {
                list.add(buildListItem(user));
            });
            JPanel panelItems = new JPanel();
            panelItems.setLayout(new BorderLayout());
            panelItems.add(list, BorderLayout.NORTH);
            return panelItems;
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return new JPanel();
    }

    /**
     * Creates the user list item
     *
     * @return JComponent
     */
    private JComponent buildListItem(User user) {
      
        // Solve challenge
        JButton solve = new JButton();
        Messages.get().message("challenge").subscribe((msg) -> {
            solve.setIcon(IconFactory.get().create("fa:filter"));
            solve.setText(msg);
        });
        solve.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(MissionUserPanel.this).dispose();
            MissionUserChallengePanel.showDialog(user);
        });
        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(solve, BorderLayout.NORTH);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
        // Image
        BufferedImage image = ImageScale.scale(user.getImageBuffered(), THUMBNAIL_SIZE);
        JPanel imagePanel = new JPanel();
        imagePanel.add(new JLabel(new ImageIcon(image)));
        // Name
        JLabel name = new JLabel();
        name.setText(user.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Xp
        JLabel xp = new JLabel(String.format("%s xp", user.getXp()));
        xp.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        // Header
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        header.add(name);
        // Container
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(2, 1));
        container.add(header);
        container.add(xp);
        // Builds the component
        JPanel component = new JPanel();
        component.setLayout(new FlowLayout());
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(imagePanel);
        component.add(container);
        component.add(container);
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component, BorderLayout.WEST);
        itemComponent.add(buttonsPanel, BorderLayout.EAST);
        return itemComponent;

    }

}
