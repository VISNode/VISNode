package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.commons.swing.WindowFactory;
import visnode.commons.swing.components.MarkdownViewer;
import visnode.gui.IconFactory;

/**
 * Challenge problem panel
 */
public class MissionProblemPanel extends JPanel {

    /** Mission */
    private final Mission mission;
    
    /**
     * Creates a new problem panel
     */
    private MissionProblemPanel(Mission mission) {
        this.mission = mission;
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        showDialog(ChallengeController.get().getMission());
    }
    
    /**
     * Shows the dialog
     * 
     * @param mission
     */
    public static void showDialog(Mission mission) {
        Messages.get().message("challenge.mission").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new MissionProblemPanel(mission));
            }).setVisible(true);
        });
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildContainer());
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Build the buttons
     * 
     * @return JComponent
     */
    private JComponent buildButtons() {
        JButton button = new JButton();
        Messages.get().message("challenge.goChallenge").subscribe((msg) -> {
            button.setText(msg);
            button.setIcon(IconFactory.get().create("fa:forward"));
        }).dispose();
        button.addActionListener((evt) -> {
            SwingUtilities.getWindowAncestor(MissionProblemPanel.this).dispose();
        });
        JComponent panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
    
    /**
     * Build the problem container
     *
     * @return JComponent
     */
    private JComponent buildContainer() {
        MarkdownViewer viewer = new MarkdownViewer();
        if (mission.isProblemUrl()) {
            viewer.loadUrl(mission.getProblem());
        } else {
            viewer.load(mission.getProblem());
        }
        return viewer;
    }

}
