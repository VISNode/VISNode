package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.ChallengeRepository;
import visnode.repository.MissionUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.User;
import visnode.user.UserController;

/**
 * Challenge conquest panel
 */
public class ChallengeConquestPanel extends JPanel {

    /** Image width */
    private static final int IMAGE_WIDTH = 200;
    /** User */
    private final User user;

    public ChallengeConquestPanel(User user) {
        this.user = user;
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        showDialog(UserController.get().getUser());
    }

    /**
     * Shows the dialog
     *
     * @param user
     */
    public static void showDialog(User user) {
        Messages.get().message("challenge.conquest").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new ChallengeConquestPanel(user));
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
     * Creates the conquest list
     *
     * @return JComponent
     */
    private JComponent buildList() {
        JPanel containerItems = new JPanel();
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
            SwingUtilities.getWindowAncestor(ChallengeConquestPanel.this).dispose();
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
            ChallengeRepository.get().getAll().forEach((mission) -> {
                list.add(buildListItem(mission));
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
     * Creates the mission list item
     *
     * @return JComponent
     */
    private JComponent buildListItem(Challenge challenge) {
        int missionCompleted = getChallenges(challenge);
        // Puzzle
        JPanel puzzle = new JPanel();
        puzzle.setPreferredSize(new Dimension(IMAGE_WIDTH + 12, IMAGE_WIDTH + 12));
        puzzle.setLayout(new BorderLayout());
        puzzle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        puzzle.add(new ChallengePuzzleImagePane(
                challenge.getPuzzle(),
                challenge.getPaymentBuffered(),
                missionCompleted,
                IMAGE_WIDTH
        ));
        // Title label
        JLabel label = new JLabel();
        label.setText(challenge.getName());
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Description label
        JLabel description = new JLabel(challenge.getDescription());
        description.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        description.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Challenges
        JLabel challenges = new JLabel(missionCompleted + "/" + challenge.getLevel());
        challenges.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        challenges.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JProgressBar progressBar = new JProgressBar(0, challenge.getLevel());
        progressBar.setValue(missionCompleted);
        JPanel boxChallenges = new JPanel();
        boxChallenges.setLayout(new FlowLayout(FlowLayout.LEFT));
        boxChallenges.add(progressBar);
        boxChallenges.add(challenges);
        // Description panel
        JPanel descriptionPanel = new JPanel(new GridLayout(3, 1));
        descriptionPanel.add(label);
        descriptionPanel.add(description);
        descriptionPanel.add(boxChallenges);
        JPanel boxDescriptionPanel = new JPanel();
        boxDescriptionPanel.setLayout(new BorderLayout());
        boxDescriptionPanel.add(descriptionPanel, BorderLayout.NORTH);
        // Builds the component
        JPanel component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setPreferredSize(new Dimension(0, 215));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(puzzle, BorderLayout.WEST);
        component.add(boxDescriptionPanel);
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
                    get(user, mission).
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
}
