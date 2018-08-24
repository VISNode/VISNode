package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.commons.ImageScale;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;

/**
 * Challenge message success
 */
public class ChallengeSuccessMessagePanel extends JPanel {

    /** Image width */
    private static final int IMAGE_WIDTH = 300;
    /** Window width */
    private static final int PAGE_WIDTH = 400;
    /** Mission */
    private final Mission mission;

    /**
     * Creates a challenge
     */
    private ChallengeSuccessMessagePanel(Mission mission) {
        this.mission = mission;
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     *
     * @param mission
     */
    public static void showDialog(Mission mission) {
        Messages.get().message("challenge.message").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.add(new ChallengeSuccessMessagePanel(mission));
            }).setVisible(true);
        }).dispose();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PAGE_WIDTH, 515));
        add(buildPreferences(), BorderLayout.NORTH);
        if (mission.getChallenge().isPaymentAvailable()) {
            add(buildPayment());
        }
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Builds the payment
     *
     * @return JComponent
     */
    private JComponent buildPayment() {
        BufferedImage image = ImageScale.scale(
                mission.getChallenge().getPaymentBuffered(),
                IMAGE_WIDTH
        );
        int left = (PAGE_WIDTH - image.getWidth() - 5) / 2;
        JComponent component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setBorder(BorderFactory.createEmptyBorder(10, left, 0, 0));
        component.add(new ChallengePuzzleImagePane(
                mission.getChallenge().getPuzzle(),
                image,
                mission.getLevel()
        ));
        return component;
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
        Messages.get().message("challenge.gobackChallenge").subscribe((msg) -> {
            button.setText(msg);
            button.setIcon(IconFactory.get().create("fa:forward"));
        }).dispose();
        button.addActionListener((evt) -> {
            SwingUtilities.getWindowAncestor(ChallengeSuccessMessagePanel.this).dispose();
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
        JLabel icon = new JLabel(IconFactory.get().create("fa:smile-o", 110), SwingConstants.CENTER);
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JPanel text = new JPanel();
        text.setLayout(new GridLayout(2, 1));
        Messages.get().message("challenge.success1").subscribe((msg) -> {
            text.add(new JLabel(msg, SwingConstants.CENTER));
        }).dispose();
        Messages.get().message("challenge.success2").subscribe((msg) -> {
            text.add(new JLabel(msg, SwingConstants.CENTER));
        }).dispose();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(icon, BorderLayout.NORTH);
        panel.add(text);
        return panel;
    }

}
