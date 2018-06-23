package visnode.challenge;

import visnode.repository.ChallengeUserRepository;
import visnode.repository.ChallengeRepository;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.commons.MultiFileInput;
import visnode.commons.swing.WindowFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.RepositoryException;
import visnode.user.UserController;

/**
 * The challenge list panel
 */
public class ChallengeListPanel extends JPanel {

    /** Challenge repository */
    private final ChallengeRepository repository;

    /**
     * Creates a new challenge list panel
     */
    private ChallengeListPanel() {
        super();
        this.repository = new ChallengeRepository();
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        Messages.get().message("challenge").subscribe((msg) -> {
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
     * Creates the challenge list
     *
     * @return JComponent
     */
    private JComponent buildList() {
        JPanel list = new JPanel();
        list.setLayout(new GridLayout(0, 1));
        repository.getChallenges().forEach((challenge) -> {
            list.add(buildListItem(challenge));
        });
        JPanel container = new JPanel();
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
        label.setText(challenge.getName());
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Description label
        JLabel description = new JLabel(challenge.getDescription());
        description.setForeground(description.getForeground());
        description.setBorder(BorderFactory.createEmptyBorder(1, 0, 3, 3));
        description.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        // Description panel
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridLayout(2, 1));
        descriptionPanel.add(label);
        descriptionPanel.add(description);
        // Solve challenge
        JButton solve = new JButton();
        Messages.get().message("challenge.solve").subscribe((msg) -> {
            solve.setText(msg);
        });
        solve.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(ChallengeListPanel.this).dispose();
            VISNode.get().getController().createNew();
            ChallengeController.get().start(challenge);
            File[] files = challenge.getInput().stream().map((file) -> {
                return new File(file);
            }).collect(Collectors.toList()).toArray(new File[challenge.getInput().size()]);
            VISNode.get().getModel().getNetwork().setInput(new MultiFileInput(files));
            ChallengeProblemPanel.showDialog();
        });
        JButton solutions = new JButton();
        Messages.get().message("challenge.solutions").subscribe((msg) -> {
            solutions.setText(msg);
        });
        solutions.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(ChallengeListPanel.this).dispose();
            ChallengeSolvedListPanel.showDialog(challenge.getId());
        });
        // Bottons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(solve, BorderLayout.NORTH);
        if (solved(challenge)) {
            buttonsPanel.add(solutions, BorderLayout.SOUTH);
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
     * Returns true if the challenge has solved
     *
     * @param value
     * @return boolean
     */
    private boolean solved(Challenge value) {
        try {
            return ChallengeUserRepository.get().has(UserController.get().getUserName(), value.getId());
        } catch (RepositoryException ex) {
            Logger.getLogger(ChallengeListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
