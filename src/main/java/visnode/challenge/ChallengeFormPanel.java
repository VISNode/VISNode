package visnode.challenge;

import com.github.rxsling.Buttons;
import com.github.rxsling.Labels;
import com.github.rxsling.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.commons.swing.FileChooserFactory;
import visnode.commons.swing.WindowFactory;
import visnode.commons.swing.components.CodeEditor;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.gui.UIHelper;
import visnode.repository.ChallengeRepository;
import visnode.repository.RepositoryException;

/**
 * New challenge panel
 */
public class ChallengeFormPanel extends JPanel {

    /** Name */
    private JTextField name;
    /** Description */
    private JTextArea description;
    /** Add mission button */
    private JButton addMission;
    /** Problem */
    private JButton problemButton;
    /** Payment */
    private JButton paymentButton;
    /** Challenge */
    private final Challenge challenge;
    /** Panel items */
    private JComponent panelItems;
    /** Component items */
    private JComponent componentItems;
    /** Box component */
    private JComponent boxComponent;

    /**
     * Creates a challenge
     */
    private ChallengeFormPanel(Challenge challenge) {
        this.challenge = challenge;
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        showDialog(new Challenge());
    }

    /**
     * Shows the dialog
     *
     * @param challenge
     */
    public static void showDialog(Challenge challenge) {
        WindowFactory.modal().title("New challenge").create((container) -> {
            container.add(new ChallengeFormPanel(challenge));
        }).setVisible(true);
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
        addMission.addActionListener((ev) -> {
            Mission mission = new Mission();
            mission.setLevel(challenge.size() + 1);
            Mission edited = ChallengeMissionFormPanel.showDialog(mission);
            if (edited != null) {
                challenge.addMission(mission);
                reloadItems();
            }
        });
        problemButton.addActionListener((ev) -> {
            ChallengeProblemPanel panel = new ChallengeProblemPanel();
            WindowFactory.modal().title("Problemas").create((container) -> {
                container.add(panel);
            }).setVisible(true);
            challenge.setProblem(panel.getCode());
        });
        paymentButton.addActionListener((ev) -> {
            FileChooserFactory.openImage().accept((file) -> {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                    ImageIO.write(ImageIO.read(file), "JPG", os);
                    challenge.setPayment(Base64.getEncoder().encodeToString(os.toByteArray()));
                } catch (IOException ioe) {
                    ExceptionHandler.get().handle(ioe);
                }
            });
        });
    }

    /**
     * Reloads the items
     */
    private void reloadItems() {
        SwingUtilities.invokeLater(() -> {
            componentItems.remove(panelItems);
            componentItems.add(buildPanelItens(), BorderLayout.NORTH);
            componentItems.revalidate();
            boxComponent.repaint();
        });
    }

    /**
     * Builds the button panel
     *
     * @return JComponent
     */
    private JComponent buildButtons() {
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton button = Buttons.create("Create").onClick((ev) -> {
            try {
                challenge.setName(name.getText());
                challenge.setDescription(description.getText());
                if (challenge.getName().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name is required");
                    return;
                }
                if (challenge.getDescription().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description is required");
                    return;
                }
                if (challenge.getMissions().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Missions are required");
                    return;
                }
                challenge.setPuzzle(ChallengePuzzleFactory.create(challenge.getLevel()));
                if (challenge.getId() == 0) {
                    ChallengeRepository.get().save(challenge);
                } else {
                    ChallengeRepository.get().update(challenge);
                }
                SwingUtilities.getWindowAncestor(this).dispose();
            } catch (RepositoryException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        button.setIcon(IconFactory.get().create("fa:check"));
        panel.add(button);
        return panel;
    }

    /**
     * Builds the preferences panel
     *
     * @return JComponent
     */
    private JComponent buildPreferences() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(buildItem(
                Labels.create().text(Messages.get().message("challenge.name")),
                buildName())
        );
        panel.add(buildItem(
                Labels.create().text(Messages.get().message("challenge.description")),
                buildDescription())
        );
        panel.add(buildProblem());
        panel.add(buildPaymentButton());
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(buildBoxChallenges());
        boxComponent = container;
        return ScrollFactory.pane(container).create();
    }

    /**
     * Build form item
     *
     * @param label
     * @param component
     * @return JComponent
     */
    private JComponent buildItem(JComponent label, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(component);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return panel;
    }

    /**
     * Builds the problem
     *
     * @return JComponent
     */
    private JComponent buildProblem() {
        problemButton = new JButton("Problema");
        problemButton.setIcon(IconFactory.get().create("fa:font"));
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(problemButton, BorderLayout.WEST);
        return panel;
    }

    /**
     * Builds the payment button
     *
     * @return JComponent
     */
    private JComponent buildPaymentButton() {
        paymentButton = new JButton("Recompensa");
        paymentButton.setIcon(IconFactory.get().create("fa:dollar"));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.setLayout(new BorderLayout());
        panel.add(paymentButton, BorderLayout.WEST);
        return panel;
    }

    /**
     * Builds the box challenges
     *
     * @return JComponent
     */
    private JComponent buildBoxChallenges() {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        Messages.get().message("challenge.title").subscribe((msg) -> {
            label.setText(msg);
        });
        JComponent component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        component.add(label, BorderLayout.NORTH);
        component.add(buildBoxChallengesContainer());
        return component;
    }

    /**
     * Builds the box challenges container
     *
     * @return JComponent
     */
    private JComponent buildBoxChallengesContainer() {
        addMission = new JButton();
        Messages.get().message("challenge.new").subscribe((msg) -> {
            addMission.setText(msg);
            addMission.setIcon(IconFactory.get().create("fa:plus"));
        });
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        buttons.add(addMission, BorderLayout.WEST);
        JComponent component = new JPanel();
        component.setLayout(new BorderLayout());
        component.add(buttons, BorderLayout.NORTH);
        component.add(buildChallenges());
        return component;
    }

    /**
     * Builds the box challenges
     *
     * @return JComponent
     */
    private JComponent buildChallenges() {
        componentItems = new JPanel();
        componentItems.setLayout(new BorderLayout());
        componentItems.add(buildPanelItens(), BorderLayout.NORTH);
        componentItems.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
        return componentItems;
    }

    /**
     * Builds the box challenges items
     *
     * @return JComponent
     */
    private JComponent buildPanelItens() {
        panelItems = new JPanel();
        panelItems.setLayout(new GridLayout(0, 1));
        challenge.getMissions().forEach((challenge) -> {
            panelItems.add(buildChallengesItem(challenge));
        });
        return panelItems;
    }

    /**
     * Builds the box challenges items
     *
     * @return JComponent
     */
    private JComponent buildChallengesItem(Mission mission) {
        JButton delete = new JButton();
        delete.setIcon(IconFactory.get().create("fa:trash-o"));
        delete.addActionListener((ev) -> {
            challenge.removeMission(mission);
            reloadItems();
        });
        JButton edit = new JButton();
        edit.setIcon(IconFactory.get().create("fa:pencil"));
        edit.addActionListener((ev) -> {
            ChallengeMissionFormPanel.showDialog(mission);
        });
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(delete);
        buttons.add(edit);
        JPanel component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setPreferredSize(new Dimension(0, 40));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(new JLabel("Level: " + String.valueOf(mission.getLevel())));
        component.add(buttons, BorderLayout.EAST);
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component);
        return itemComponent;
    }

    /**
     * Builds the name field
     *
     * @return JComponent
     */
    private JComponent buildName() {
        name = new JTextField();
        name.setText(challenge.getName());
        return name;
    }

    /**
     * Builds the description field
     *
     * @return JComponent
     */
    private JComponent buildDescription() {
        description = new JTextArea(5, 20);
        description.setText(challenge.getDescription());
        return new JScrollPane(description);
    }

    /**
     * Challenge parameter panel
     */
    private class ChallengeProblemPanel extends JComponent {

        /** The code editor */
        private CodeEditor textArea;

        public ChallengeProblemPanel() {
            super();
            initGui();
        }

        /**
         * Initialize the interface
         */
        private void initGui() {
            setLayout(new BorderLayout());
            add(buildCodePane());
        }

        /**
         * Builds the code pane
         *
         * @return JComponent
         */
        private JComponent buildCodePane() {
            textArea = new CodeEditor(SyntaxConstants.SYNTAX_STYLE_MAKEFILE);
            textArea.setText(challenge.getProblem());
            return textArea;
        }

        /**
         * Returns the code
         *
         * @return String
         */
        public String getCode() {
            return textArea.getText();
        }

    }

}
