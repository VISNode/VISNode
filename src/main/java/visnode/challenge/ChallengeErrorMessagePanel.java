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
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;

/**
 * Challenge error message
 */
public class ChallengeErrorMessagePanel extends JPanel {

    /**
     * Creates a challenge
     */
    private ChallengeErrorMessagePanel() {
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        WindowFactory.modal().title("Mensagem").create((container) -> {
            container.add(new ChallengeErrorMessagePanel());
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 200));
        add(buildPreferences());
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
        JButton button = new JButton("Ir para a missão", IconFactory.get().create("fa:forward"));
        button.addActionListener((evt) -> {
            SwingUtilities.getWindowAncestor(ChallengeErrorMessagePanel.this).dispose();
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
        text.add(new JLabel("O resultado obtido não é igual ao esperado!", SwingConstants.CENTER));
        text.add(new JLabel("Tente novamente!", SwingConstants.CENTER));
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(icon, BorderLayout.NORTH);
        panel.add(text);
        return panel;
    }

}
