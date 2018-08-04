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
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.ChallengeUserRepository;
import visnode.repository.MissionRepository;
import visnode.repository.MissionUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.UserController;

/**
 * The mission list panel
 */
public class MissionListPanel extends JPanel {

    /**
     * Creates a new mission list panel
     */
    private MissionListPanel() {
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
                container.add(new MissionListPanel());
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
        JPanel container = new JPanel();
        try {
            JPanel list = new JPanel();
            list.setLayout(new GridLayout(0, 1));
            MissionRepository.get().getAll().forEach((mission) -> {
                list.add(buildListItem(mission));
            });
            container.setLayout(new BorderLayout());
            container.add(list, BorderLayout.NORTH);
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return ScrollFactory.pane(container).create();
    }

    /**
     * Creates the mission list item
     *
     * @return JComponent
     */
    private JComponent buildListItem(Mission mission) {
        // Title label
        JLabel label = new JLabel();
        label.setText(mission.getName());
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Description label
        JLabel description = new JLabel(mission.getDescription());
        description.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        description.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        // MaxScore
        JLabel maxScore = new JLabel("Max Score: " + mission.getXp());
        maxScore.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        maxScore.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        // Challenges
        JLabel challenges = new JLabel(getChallenges(mission) + "/"  + mission.getLevel());
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
            solve.setText(msg);
        });
        solve.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(MissionListPanel.this).dispose();
            ChallengesPanel.showDialog(mission);
        });
        JButton solutions = new JButton();
        Messages.get().message("challenge.solutions").subscribe((msg) -> {
            solutions.setText(msg);
        });
        solutions.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(MissionListPanel.this).dispose();
            MissionSolvedListPanel.showDialog(mission);
        });
        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(solve, BorderLayout.NORTH);
        if (solved(mission)) {
            buttonsPanel.add(solutions, BorderLayout.SOUTH);
        }
        // Builds the component
        JPanel component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setPreferredSize(new Dimension(0, 75));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(descriptionPanel);
        component.add(buttonsPanel, BorderLayout.EAST);
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
    private int getChallenges(Mission mission) {
        try {
            int amount = ChallengeUserRepository.get().
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
    private boolean solved(Mission value) {
        try {
            return MissionUserRepository.get().
                    has(UserController.get().getUser(), value);
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return false;
    }
}
