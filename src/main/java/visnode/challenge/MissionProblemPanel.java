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

    /**
     * Creates a new problem panel
     */
    private MissionProblemPanel() {
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        Messages.get().message("challenge").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new MissionProblemPanel());
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
        JButton button = new JButton("Ir para o desafio", IconFactory.get().create("fa:forward"));
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
        if (ChallengeController.get().getMission().isProblemUrl()) {
            viewer.loadUrl(ChallengeController.get().getMission().getProblem());
        } else {
            viewer.load(ChallengeController.get().getMission().getProblem());
        }
        return viewer;
    }

}
