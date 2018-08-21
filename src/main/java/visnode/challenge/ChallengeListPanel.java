package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.MissionUserRepository;
import visnode.repository.ChallengeRepository;
import visnode.repository.ChallengeUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.UserController;

/**
 * The challenge list panel
 */
public class ChallengeListPanel extends JPanel {

    /** Items container */
    private JComponent containerItems;
    /** Items panel */
    private JComponent panelItems;

    /**
     * Creates a new mission list panel
     */
    private ChallengeListPanel() {
        super();
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        Messages.get().message("challenge.mission").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new ChallengeListPanel());
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
     * Reload the items
     */
    private void reloadItems() {
        SwingUtilities.invokeLater(() -> {
            containerItems.remove(panelItems);
            containerItems.add(buildListComponent());
            containerItems.revalidate();
        });
    }

    /**
     * Creates the mission list
     *
     * @return JComponent
     */
    private JComponent buildList() {
        containerItems = new JPanel();
        containerItems.setLayout(new BorderLayout());
        if (UserController.get().getUser().isUserEditor()) {
            containerItems.add(buildButtons(), BorderLayout.NORTH);
        }
        containerItems.add(buildListComponent());
        return ScrollFactory.pane(containerItems).create();
    }

    /**
     * Builds the buttons container
     *
     * @return JComponent
     */
    private JComponent buildButtons() {
        JButton button = new JButton();
        Messages.get().message("challenge.new").subscribe((msg) -> {
            button.setIcon(IconFactory.get().create("fa:plus"));
            button.setText(msg);
        });
        button.addActionListener((ev) -> {
            ChallengeFormPanel.showDialog();
            reloadItems();
        });
        JComponent component = new JPanel();
        component.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        component.setLayout(new BorderLayout());
        component.add(button, BorderLayout.EAST);
        return component;
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
            ChallengeRepository.get().getAll().forEach((mission) -> {
                list.add(buildListItem(mission));
            });
            panelItems = new JPanel();
            panelItems.setLayout(new BorderLayout());
            panelItems.add(list, BorderLayout.NORTH);
            return panelItems;
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return new JPanel();
    }

    /**
     * Creates the mission list item
     *
     * @return JComponent
     */
    private JComponent buildListItem(Challenge challenge) {
        // Title label
        JLabel label = new JLabel();
        label.setText(challenge.getName());
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Description label
        JLabel description = new JLabel(challenge.getDescription());
        description.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        description.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        // MaxScore
        JLabel maxScore = new JLabel("Max Score: " + challenge.getXp());
        maxScore.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        maxScore.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        // Challenges
        JLabel challenges = new JLabel(getChallenges(challenge) + "/" + challenge.getLevel());
        challenges.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        challenges.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        // Description panel
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridLayout(4, 1));
        descriptionPanel.add(label);
        descriptionPanel.add(description);
        descriptionPanel.add(maxScore);
        descriptionPanel.add(challenges);
        // Solve challenge
        JButton solve = new JButton();
        Messages.get().message("challenge.solve").subscribe((msg) -> {
            solve.setIcon(IconFactory.get().create("fa:play"));
            solve.setText(msg);
        });
        solve.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(ChallengeListPanel.this).dispose();
            MissionsPanel.showDialog(challenge);
        });
        JButton update = new JButton();
        Messages.get().message("challenge.update").subscribe((msg) -> {
            update.setIcon(IconFactory.get().create("fa:pencil"));
            update.setText(msg);
        });
        update.addActionListener((ev) -> {
            ChallengeFormPanel.showDialog(challenge);
            reloadItems();
        });
        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(solve, BorderLayout.NORTH);
        JPanel actions = new JPanel();
        actions.setLayout(new BorderLayout());
        actions.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        actions.add(update, BorderLayout.NORTH);
        JPanel rightComponent = new JPanel();
        rightComponent.setLayout(new BorderLayout());
        if (UserController.get().getUser().isUserEditor()) {
            rightComponent.add(actions, BorderLayout.WEST);
        }
        rightComponent.add(buttonsPanel, BorderLayout.EAST);
        // Builds the component
        JPanel component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setPreferredSize(new Dimension(0, 75));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(descriptionPanel);
        component.add(rightComponent, BorderLayout.EAST);
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component);
        return itemComponent;
    }

    /**
     * Returns the amount of challenges completes
     *
     * @param mission
     * @return int
     */
    private int getChallenges(Challenge mission) {
        try {
            int amount = MissionUserRepository.get().
                    get(UserController.get().getUser(), mission).
                    size();
            if (amount > mission.getLevel()) {
                return mission.getLevel();
            }
            return amount;
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return 0;
    }

    /**
     * Returns true if the mission has solved
     *
     * @param value
     * @return boolean
     */
    private boolean solved(Challenge value) {
        try {
            return ChallengeUserRepository.get().
                    has(UserController.get().getUser(), value);
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return false;
    }
}
