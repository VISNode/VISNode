package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.commons.MultiFileInput;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.MissionUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.UserController;

/**
 * The challenge list panel
 */
public class MissionsPanel extends JPanel {

    /** The mission identifier */
    private final Challenge challenge;
    /** Mission completed */
    private int missionCompleted = 0;

    /**
     * Creates a new challenge list panel
     *
     * @param challenge
     */
    private MissionsPanel(Challenge challenge) {
        super();
        this.challenge = challenge;
        initGui();
    }

    /**
     * Shows the dialog
     *
     * @param challenge
     */
    public static void showDialog(Challenge challenge) {
        if (challenge.getMissions().isEmpty()) {
            return;
        }
        Mission mission = challenge.getMissions().get(0);
        boolean solved = solved(mission);
        if (!solved) {
            openChallenge(challenge);
        }
        if (challenge.getMissions().size() == 1 && !solved) {
            start(mission);
            return;
        }
        Messages.get().message("challenge").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new MissionsPanel(challenge));
            }).setVisible(true);
        });
    }

    /**
     * Open challenge problem
     *
     * @param challenge
     */
    private static void openChallenge(Challenge challenge) {
        ChallengeProblemPanel.showDialog(challenge);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildToolbar(), BorderLayout.NORTH);
        add(buildList());
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Builds the tool bar
     *
     * @return
     */
    private JComponent buildToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.add(new JButton(new ActionOpenChallenge()));
        toolbar.add(new JButton(new ActionOpenPayments()));
        return toolbar;
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
        challenge.getMissions().forEach((mission) -> {
            list.add(buildListItem(mission));
        });
        container.setLayout(new BorderLayout());
        container.add(list, BorderLayout.NORTH);
        JScrollPane scrollPane = ScrollFactory.pane(container).create();
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
            SwingUtilities.getWindowAncestor(MissionsPanel.this).dispose();
        });
        JComponent panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        return panel;
    }

    /**
     * Creates the challenge list item
     *
     * @return JComponent
     */
    private JComponent buildListItem(Mission mission) {
        boolean missionSolved = solved(mission);
        if (missionSolved) {
            missionCompleted++;
        }
        // Title label
        JLabel label = new JLabel();
        label.setText("Level: " + mission.getLevel());
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // MaxScore
        JLabel maxScore = new JLabel("Max Score: " + mission.getXp());
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
            solve.setIcon(IconFactory.get().create("fa:play"));
        });
        solve.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(MissionsPanel.this).dispose();
            start(mission);
        });
        JButton solutions = new JButton();
        Messages.get().message("challenge.solutions").subscribe((msg) -> {
            solutions.setIcon(IconFactory.get().create("fa:clipboard"));
            solutions.setText(msg);
        });
        solutions.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(MissionsPanel.this).dispose();
            ChallengeSolvedListPanel.showDialog(mission);
        });
        if (mission.getLevel() > missionCompleted + 1) {
            solve.setEnabled(false);
        }
        // Actions
        GridLayout actionsLayout = new GridLayout(2, 1);
        actionsLayout.setVgap(5);
        JPanel actions = new JPanel();
        actions.setLayout(actionsLayout);
        if (!missionSolved) {
            actions.add(solve);
        }
        if (missionSolved) {
            actions.add(solutions);
        }
        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(actions, BorderLayout.NORTH);
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
     * @param mission
     */
    private static void start(Mission mission) {
        VISNode.get().getController().createNew();
        try {
            List<MissionUser> missionUser = MissionUserRepository.get().
                    get(UserController.get().getUser(),
                            mission.getChallenge(),
                            mission.getLevel());
            if (!missionUser.isEmpty()) {
                ChallengeController.get().openProject(missionUser.
                        get(missionUser.size() - 1).
                        getSubmission(), mission);
            }

        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        ChallengeController.get().start(mission);
        File[] files = mission.getInputFiles().stream().map((file) -> {
            return file;
        }).collect(Collectors.toList()).toArray(new File[mission.getInput().size()]);
        VISNode.get().getModel().getNetwork().setInput(new MultiFileInput(files));
        MissionProblemPanel.showDialog();
    }

    /**
     * Returns true if the challenge has solved
     *
     * @param value
     * @return boolean
     */
    private static boolean solved(Mission value) {
        try {
            return MissionUserRepository.get().
                    has(UserController.get().getUser(), value);
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return false;
    }

    /**
     * Action for open the challenge
     */
    private class ActionOpenChallenge extends AbstractAction {

        public ActionOpenChallenge() {
            super("Desafio", IconFactory.get().create("fa:question"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            openChallenge(challenge);
        }

    }

    /**
     * Action open payments
     */
    private class ActionOpenPayments extends AbstractAction {

        public ActionOpenPayments() {
            super("Recompensa", IconFactory.get().create("fa:dollar"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ChallengePuzzlePane.showDialog(challenge, missionCompleted);
        }

    }

}
