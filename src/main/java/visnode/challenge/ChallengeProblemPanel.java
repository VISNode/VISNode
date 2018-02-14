package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JPanel;
import visnode.application.Messages;
import visnode.commons.swing.WindowFactory;
import visnode.commons.swing.components.MarkdownViewer;

/**
 * Challenge problem panel
 */
public class ChallengeProblemPanel extends JPanel {

    /**
     * Creates a new problem panel
     */
    private ChallengeProblemPanel() {
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        Messages.get().message("challenge").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new ChallengeProblemPanel());
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
    }

    /**
     * Build the problem container
     *
     * @return JComponent
     */
    private JComponent buildContainer() {
        MarkdownViewer viewer = new MarkdownViewer();
        viewer.loadUrl(ChallengeController.get().getChallenge().getProblem());
        return viewer;
    }

}
