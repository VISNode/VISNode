package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import visnode.commons.swing.WindowFactory;

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
        setPreferredSize(new Dimension(800, 500));
        add(buildPreferences());
    }

    /**
     * Initializes the events
     */
    private void initEvents() {

    }

    /**
     * Builds the preferences panel
     *
     * @return JComponent
     */
    private JComponent buildPreferences() {
        return new JLabel("The output is incorrect! :(");
    }

}
