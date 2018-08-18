package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.ChallengeRepository;
import visnode.repository.MissionUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.User;

/**
 * The challenge user panel
 */
public class MissionUserChallengePanel extends JPanel {

    /** User */
    private final User user;

    /**
     * Creates a new mission list panel
     *
     * @param user
     */
    private MissionUserChallengePanel(User user) {
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
                container.add(new MissionUserChallengePanel(user));
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
        add(buildButtons(), BorderLayout.SOUTH);
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
        JScrollPane scrollPane = ScrollFactory.pane(containerItems).create();
        scrollPane.setBorder(null);
        return scrollPane;
    }

    /**
     * Build the buttons
     *
     * @return JComponent
     */
    private JComponent buildButtons() {
        JButton button = new JButton();
        Messages.get().message("next").subscribe((msg) -> {
            button.setText(msg);
            button.setIcon(IconFactory.get().create("fa:check"));
        });
        button.addActionListener((evt) -> {
            SwingUtilities.getWindowAncestor(MissionUserChallengePanel.this).dispose();
        });
        JComponent panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        return panel;
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
            List<MissionUser> missions = MissionUserRepository.get().get(user);
            missions.stream().
                    collect(Collectors.groupingBy((it) -> it.getChallenge())).
                    forEach((key, value) -> {
                        try {
                            list.add(buildListItem(value,
                                    ChallengeRepository.get().get(key)
                            ));
                        } catch (RepositoryException ex) {
                            ExceptionHandler.get().handle(ex);
                        }
                    });
            if (missions.isEmpty()) {
                JPanel panelMessage = new JPanel();
                panelMessage.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 30));
                panelMessage.add(new JLabel("Usuário não possui nenhuma missão finalizada", SwingConstants.CENTER));
                list.add(panelMessage);
                setPreferredSize(new Dimension(800, 250));
            }
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
    private JComponent buildListItem(List<MissionUser> missionUser, Challenge challenge) {
        int xpMissions = missionUser.stream().reduce((ac, it) -> {
            MissionUser userAc = new MissionUser();
            userAc.setXp(ac.getXp() + it.getXp());
            return userAc;
        }).get().getXp();
        // Name
        JLabel name = new JLabel();
        name.setText(challenge.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Description
        JLabel description = new JLabel(challenge.getDescription());
        description.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        // Xp
        JLabel xp = new JLabel("Xp: " + xpMissions + "/" + challenge.getXp());
        xp.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        // Container
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(3, 1));
        container.add(name);
        container.add(description);
        container.add(xp);
        // Builds the component
        JPanel component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(container);
        JPanel panelMission = new JPanel();
        panelMission.setLayout(new GridLayout(missionUser.size(), 1));
        missionUser.forEach((item) -> {
            Optional<Mission> mission = challenge.getMissions().stream().
                    filter((it) -> it.getId() == item.getIdMission()).
                    findFirst();
            if (mission.isPresent()) {
                panelMission.add(
                        buildListItemMission(
                                item,
                                mission.get()
                        )
                );
            }
        });
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component, BorderLayout.NORTH);
        itemComponent.add(panelMission);
        return itemComponent;
    }

    /**
     * Creates the user list item
     *
     * @return JComponent
     */
    private JComponent buildListItemMission(MissionUser missionUser, Mission mission) {
        // Solve challenge
        JButton open = new JButton();
        Messages.get().message("open").subscribe((msg) -> {
            open.setIcon(IconFactory.get().create("fa:folder-open"));
            open.setText(msg);
        });
        open.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(MissionUserChallengePanel.this).dispose();
            VISNode.get().getController().open(missionUser.getSubmission());
        });
        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(open, BorderLayout.NORTH);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
        // Name
        JLabel name = new JLabel();
        name.setText("Level: " + missionUser.getLevel());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Xp
        JLabel xp = new JLabel("Xp: " + missionUser.getXp() + "/" + mission.getXp());
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
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component, BorderLayout.WEST);
        itemComponent.add(buttonsPanel, BorderLayout.EAST);
        return itemComponent;
    }

}
