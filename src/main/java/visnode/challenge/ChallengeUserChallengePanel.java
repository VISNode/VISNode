package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.MissionRepository;
import visnode.repository.MissionUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.User;

/**
 * The challenge user panel
 */
public class ChallengeUserChallengePanel extends JPanel {

    /** User */
    private final User user;

    /**
     * Creates a new mission list panel
     *
     * @param user
     */
    private ChallengeUserChallengePanel(User user) {
        super();
        this.user = user;
        initGui();
    }

    /**
     * Shows the dialog
     *
     * @param user
     */
    public static void showDialog(User user) {
        Messages.get().message("user").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new ChallengeUserChallengePanel(user));
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
            MissionUserRepository.get().get(user).forEach((missionUser) -> {
                try {
                    list.add(buildListItem(missionUser, MissionRepository.get().get(missionUser.getIdMission())));
                } catch (RepositoryException ex) {
                    ExceptionHandler.get().handle(ex);
                }
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
    private JComponent buildListItem(MissionUser missionUser, Mission mission) {
        // Solve challenge
        JButton open = new JButton();
        Messages.get().message("open").subscribe((msg) -> {
            open.setIcon(IconFactory.get().create("fa:folder-open"));
            open.setText(msg);
        });
        open.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(ChallengeUserChallengePanel.this).dispose();
            VISNode.get().getController().open(missionUser.getSubmission());
        });
        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(open, BorderLayout.NORTH);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
        // Name
        JLabel name = new JLabel();
        name.setText(mission.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Xp
        JLabel xp = new JLabel(mission.getDescription());
        xp.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        // Container
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(2, 1));
        container.add(name);
        container.add(xp);
        // Builds the component
        JPanel component = new JPanel();
        component.setLayout(new FlowLayout());
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(container);
        component.add(container);
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component, BorderLayout.WEST);
        itemComponent.add(buttonsPanel, BorderLayout.EAST);
        return itemComponent;

    }

}
