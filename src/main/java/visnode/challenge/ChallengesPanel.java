package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.commons.MultiFileInput;
import visnode.commons.swing.WindowFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.ChallengeUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.UserController;

/**
 * The challenge list panel
 */
public class ChallengesPanel extends JPanel {

    /** The mission identifier */
    private final Mission mission;

    /**
     * Creates a new challenge list panel
     *
     * @param mission
     */
    private ChallengesPanel(Mission mission) {
        super();
        this.mission = mission;
        initGui();
    }

    /**
     * Shows the dialog
     *
     * @param mission
     */
    public static void showDialog(Mission mission) {
        Messages.get().message("challenge").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new ChallengesPanel(mission));
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
        JPanel list = new JPanel();
        list.setLayout(new GridLayout(0, 1));
        mission.getChallenges().forEach((challenge) -> {
            list.add(buildListItem(challenge));
        });
        container.setLayout(new BorderLayout());
        container.add(list, BorderLayout.NORTH);
        return ScrollFactory.pane(container).create();
    }

    /**
     * Creates the challenge list item
     *
     * @return JComponent
     */
    private JComponent buildListItem(Challenge challenge) {
        // Title label
        JLabel label = new JLabel();
        label.setText("Level: " + challenge.getLevel());
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // MaxScore
        JLabel maxScore = new JLabel("Max Score: " + challenge.getXp());
        maxScore.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        maxScore.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        // Description panel
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridLayout(3, 1));
        descriptionPanel.add(label);
        descriptionPanel.add(maxScore);
        // Solve challenge
        JButton solve = new JButton();
        Messages.get().message("challenge.solve").subscribe((msg) -> {
            solve.setText(msg);
        });
        solve.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(ChallengesPanel.this).dispose();
            start(challenge);
        });
        // Bottons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        if (!solved(challenge)) {
            buttonsPanel.add(solve, BorderLayout.NORTH);
        }
        // Builds the component
        JPanel component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setPreferredSize(new Dimension(0, 70));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(descriptionPanel);
        component.add(buttonsPanel, BorderLayout.EAST);
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component);
        return itemComponent;
    }

    /**
     * Starts the challenge
     *
     * @param challenge
     */
    private void start(Challenge challenge) {
        VISNode.get().getController().createNew();
        if (!challenge.isFirtLevel()) {
            try {
                VISNode.get().getController().open(
                        ChallengeUserRepository.get().
                                get(UserController.get().getUser(),
                                        challenge.getMission(),
                                        challenge.getLevel() - 1).
                                get(0).
                                getSubmission()
                );
            } catch (RepositoryException ex) {
                ExceptionHandler.get().handle(ex);
            }
        }
        ChallengeController.get().start(challenge);
        File[] files = challenge.getInput().stream().map((file) -> {
            return new File(file.getValue());
        }).collect(Collectors.toList()).toArray(new File[challenge.getInput().size()]);
        VISNode.get().getModel().getNetwork().setInput(new MultiFileInput(files));
        ChallengeProblemPanel.showDialog();
    }

    /**
     * Returns true if the challenge has solved
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
