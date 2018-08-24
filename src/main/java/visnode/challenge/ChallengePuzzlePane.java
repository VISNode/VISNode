package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;

/**
 * Challenge Puzzle Pane
 */
public class ChallengePuzzlePane extends JPanel {

    /** Challenge */
    private final Challenge challenge;
    /** Missions completed */
    private final int missionCompleted;

    /**
     * Creates a challenge
     */
    private ChallengePuzzlePane(Challenge challenge, int missionCompleted) {
        this.challenge = challenge;
        this.missionCompleted = missionCompleted;
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     *
     * @param challenge
     * @param missionCompleted
     */
    public static void showDialog(Challenge challenge, int missionCompleted) {
        Messages.get().message("challenge.payment").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.add(new ChallengePuzzlePane(challenge, missionCompleted));
            }).setVisible(true);
        }).dispose();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildPreferences());
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Initializes the events
     */
    private void initEvents() {

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
            SwingUtilities.getWindowAncestor(ChallengePuzzlePane.this).dispose();
        });
        JComponent panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
    
    /**
     * Builds the preferences panel
     *
     * @return JComponent
     */
    private JComponent buildPreferences() {
        if (!challenge.isPaymentAvailable()) {
            return new JPanel();
        }
        BufferedImage image = challenge.getPaymentBuffered();
        setPreferredSize(new Dimension(image.getWidth() + 5, image.getHeight() + 45));
        return new ChallengePuzzleImagePane(challenge.getPuzzle(), image, missionCompleted);
    }
   
}
