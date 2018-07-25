package visnode.challenge;

import com.github.rxsling.Buttons;
import com.github.rxsling.Labels;
import com.github.rxsling.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.commons.ImageScale;
import visnode.commons.swing.FileChooserFactory;
import visnode.commons.swing.WindowFactory;
import visnode.commons.swing.components.CodeEditor;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;

/**
 * New mission panel
 */
public class MissionChallengeFormPanel extends JPanel {

    private static final int THUMBNAIL_SIZE = 100;

    /** Xp */
    private JFormattedTextField xp;
    /** Adds parameters */
    private JButton parameterButton;
    /** Problem */
    private JButton problemButton;
    /** Challenge */
    private final Challenge challenge;
    /** Challenge output */
    private Challenge challengeOutput;
    /** Items panel */
    private JComponent panelItems;
    /** Items component */
    private JComponent componentItems;

    /**
     * Creates a mission
     */
    private MissionChallengeFormPanel(Challenge challenge) {
        this.challenge = challenge == null ? new Challenge() : challenge;
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     *
     * @param challenge
     * @return Challenge
     */
    public static Challenge showDialog(Challenge challenge) {
        MissionChallengeFormPanel panel = new MissionChallengeFormPanel(challenge);
        WindowFactory.modal().title("New challenge").create((container) -> {
            container.add(panel);
        }).setVisible(true);
        return panel.challengeOutput;
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        add(buildPreferences(), BorderLayout.NORTH);
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Initializes the events
     */
    private void initEvents() {
        parameterButton.addActionListener((ev) -> {
            WindowFactory.modal().title("Parameter").create((container) -> {
                container.add(new ChallengeParameterPanel());
            }).setVisible(true);
            reloadItems();
        });
        problemButton.addActionListener((ev) -> {
            ChallengeProblemPanel panel = new ChallengeProblemPanel();
            WindowFactory.modal().title("Problem").create((container) -> {
                container.add(panel);
            }).setVisible(true);
            challenge.setProblem(panel.getCode());
        });
    }

    /**
     * Reload items
     */
    private void reloadItems() {
        SwingUtilities.invokeLater(() -> {
            componentItems.remove(panelItems);
            componentItems.add(buildPanelItens(), BorderLayout.NORTH);
            componentItems.revalidate();
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
        panel.add(Buttons.create("Create").onClick((ev) -> {
            challenge.setXp(Integer.parseInt(xp.getText()));
            challengeOutput = challenge;
            SwingUtilities.getWindowAncestor(this).dispose();
        }));
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
                Labels.create().text("xp"),
                buildXp())
        );
        panel.add(buildProblem());
        panel.add(buildBoxChallenges("Parameters"));
        return panel;
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
        return panel;
    }

    /**
     * Builds the problem
     *
     * @return JComponent
     */
    private JComponent buildProblem() {
        problemButton = new JButton("Problem");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(problemButton, BorderLayout.WEST);
        return panel;
    }

    /**
     * Builds the challenge box
     *
     * @param type
     * @return JComponent
     */
    private JComponent buildBoxChallenges(String type) {
        JLabel label = new JLabel(type);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JComponent component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        component.add(label, BorderLayout.NORTH);
        component.add(buildBoxChallengesContainer());
        return component;
    }

    /**
     * Builds the challenge container
     *
     * @return JComponent
     */
    private JComponent buildBoxChallengesContainer() {
        parameterButton = new JButton();
        Messages.get().message("challenge.new").subscribe((msg) -> {
            parameterButton.setText(msg);
        });
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        buttons.add(parameterButton, BorderLayout.WEST);
        JComponent component = new JPanel();
        component.setLayout(new BorderLayout());
        component.add(buttons, BorderLayout.NORTH);
        component.add(buildChallenges());
        return component;
    }

    /**
     * Builds the challenges box
     *
     * @return JComponent
     */
    private JComponent buildChallenges() {
        componentItems = new JPanel();
        componentItems.setLayout(new BorderLayout());
        componentItems.add(buildPanelItens(), BorderLayout.NORTH);
        return componentItems;
    }

    /**
     * Builds the challenge items
     *
     * @return JComponent
     */
    private JComponent buildPanelItens() {
        JPanel itens = new JPanel();
        itens.setLayout(new GridLayout(0, 1));
        challenge.getInput().forEach((item) -> {
            itens.add(buildChallengesItem(item));
        });
        panelItems = ScrollFactory.pane(itens).create();
        panelItems.setPreferredSize(new Dimension(0, 300));
        return panelItems;
    }

    /**
     * Builds the challenge items
     *
     * @param file
     * @return JComponent
     */
    private JComponent buildChallengesItem(ChallengeValue file) {
        JButton delete = new JButton();
        delete.setIcon(IconFactory.get().create("fa:trash-o"));
        delete.addActionListener((ev) -> {
            challenge.removeOutput(challenge.
                    getOutput().
                    get(challenge.getInput().indexOf(file))
            );
            challenge.removeInput(file);
            reloadItems();
        });
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(delete);
        JPanel component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setPreferredSize(new Dimension(0, 40));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        try {
            component.add(new JLabel(getIcon(file)), BorderLayout.WEST);
        } catch (IOException ex) {
            ExceptionHandler.get().handle(ex);
        }
        component.add(buttons, BorderLayout.EAST);
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component);
        return itemComponent;

    }

    /**
     * Returns the user image icon
     *
     * @return ImageIcon
     */
    private ImageIcon getIcon(ChallengeValue value) throws IOException {
        return new ImageIcon(
                ImageScale.scale(value.getValueBufferedImage(), THUMBNAIL_SIZE)
        );
    }

    /**
     * Builds the description field
     *
     * @return JComponent
     */
    private JComponent buildXp() {
        xp = new JFormattedTextField(new DecimalFormat("#0"));
        if (challenge.getXp() > 0) {
            xp.setText(String.valueOf(challenge.getXp()));
        }
        return xp;
    }

    /**
     * Challenge parameter panel
     */
    private class ChallengeParameterPanel extends JComponent {

        /** Input button */
        private JButton inputButton;
        /** Output button */
        private JButton outputButton;
        /** Output text */
        private JTextField outputText;
        /** Input file */
        private File inputFile;
        /** Output file */
        private File outputFile;

        /**
         * Creates a mission
         */
        private ChallengeParameterPanel() {
            initGui();
            initEvents();
        }

        /**
         * Initializes the interface
         */
        private void initGui() {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(800, 600));
            add(buildPreferences(), BorderLayout.NORTH);
            add(buildButtons(), BorderLayout.SOUTH);
        }

        /**
         * Initializes the events
         */
        private void initEvents() {
            inputButton.addActionListener((ev) -> {
                FileChooserFactory.openImage().accept((file) -> {
                    inputFile = file;
                });
            });
            outputButton.addActionListener((ev) -> {
                FileChooserFactory.openImage().accept((file) -> {
                    outputFile = file;
                });
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
            panel.add(Buttons.create("Ok").onClick((ev) -> {
                if (inputFile == null) {
                    JOptionPane.showMessageDialog(null, "Input is required");
                    return;
                }
                if (outputFile == null && outputText.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ouput is required");
                    return;
                }
                challenge.addInput(new ChallengeValue(inputFile));
                challenge.addOutput(buildChallengeValue());
                SwingUtilities.getWindowAncestor(this).dispose();
            }));
            return panel;
        }

        /**
         * builds the challenge value
         *
         * @return ChallengeValue
         */
        private ChallengeValue buildChallengeValue() {
            if (!outputText.getText().isEmpty()) {
                return new ChallengeValue(
                        ChallengeValueType.TEXT,
                        outputText.getText()
                );
            }
            return new ChallengeValue(outputFile);
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
                    Labels.create().text("Input image"),
                    buildInput())
            );
            panel.add(buildItem(
                    Labels.create().text("Output image"),
                    buildOutput())
            );
            panel.add(buildItem(
                    Labels.create().text("Output text"),
                    buildOutputText())
            );
            return panel;
        }

        /**
         * Builds the input component
         *
         * @return JComponent
         */
        private JComponent buildInput() {
            inputButton = new JButton("Input");
            return inputButton;
        }

        /**
         * Builds the output component
         *
         * @return JComponent
         */
        private JComponent buildOutput() {
            outputButton = new JButton("Output");
            return outputButton;
        }

        /**
         * Builds the output text component
         *
         * @return JComponent
         */
        private JComponent buildOutputText() {
            outputText = new JTextField();
            return outputText;
        }

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
