package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;

/**
 * Challenge error message
 */
public class MissionErrorMessagePanel extends JPanel {

    /**
     * Creates a challenge
     */
    private MissionErrorMessagePanel() {
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        Messages.get().message("challenge.message").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.add(new MissionErrorMessagePanel());
            }).setVisible(true);
        }).dispose();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 200));
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
        Messages.get().message("challenge.goMission").subscribe((msg) -> {
            button.setText(msg);
            button.setIcon(IconFactory.get().create("fa:forward"));
        }).dispose();
        button.addActionListener((evt) -> {
            SwingUtilities.getWindowAncestor(MissionErrorMessagePanel.this).dispose();
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
        JLabel icon = new JLabel(IconFactory.get().create("fa:frown-o", 110), SwingConstants.CENTER);
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JPanel text = new JPanel();
        text.setLayout(new GridLayout(2, 1));
        Messages.get().message("challenge.missionError1").subscribe((msg) -> {
            text.add(new JLabel(msg, SwingConstants.CENTER));
        }).dispose();
        Messages.get().message("challenge.missionError2").subscribe((msg) -> {
            text.add(new JLabel(msg, SwingConstants.CENTER));
        }).dispose();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(icon, BorderLayout.NORTH);
        panel.add(text);
        return panel;
    }

}
