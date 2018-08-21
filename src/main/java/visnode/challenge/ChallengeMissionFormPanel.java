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
import visnode.gui.UIHelper;

/**
 * New mission panel
 */
public class ChallengeMissionFormPanel extends JPanel {

    private static final int THUMBNAIL_SIZE = 100;

    /** Xp */
    private JFormattedTextField xp;
    /** Adds parameters */
    private JButton parameterButton;
    /** Problem */
    private JButton problemButton;
    /** Mission */
    private final Mission challenge;
    /** Mission output */
    private Mission missionOutput;
    /** Items panel */
    private JComponent panelItems;
    /** Items component */
    private JComponent componentItems;
    /** Box component */
    private JComponent boxComponent;

    /**
     * Creates a mission
     */
    private ChallengeMissionFormPanel(Mission mission) {
        this.challenge = mission == null ? new Mission() : mission;
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     *
     * @param mission
     * @return Mission
     */
    public static Mission showDialog(Mission mission) {
        ChallengeMissionFormPanel panel = new ChallengeMissionFormPanel(mission);
        WindowFactory.modal().title("New mission").create((container) -> {
            container.add(panel);
        }).setVisible(true);
        return panel.missionOutput;
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
        parameterButton.addActionListener((ev) -> {
            WindowFactory.modal().title("Parâmetros").create((container) -> {
                container.add(new MissionParameterPanel());
            }).setVisible(true);
            reloadItems();
        });
        problemButton.addActionListener((ev) -> {
            MissionProblemPanel panel = new MissionProblemPanel();
            WindowFactory.modal().title("Problemas").create((container) -> {
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
        JButton button = Buttons.create("Ok").onClick((ev) -> {
            if (!xp.getText().isEmpty()) {
                challenge.setXp(Integer.parseInt(xp.getText()));
            }
            if (challenge.getXp() == 0) {
                JOptionPane.showMessageDialog(null, "Xp is required");
                return;
            }
            if (challenge.getProblem() == null || challenge.getProblem().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Problem is required");
                return;
            }
            if (challenge.getInput().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Parameters are required");
                return;
            }
            missionOutput = challenge;
            SwingUtilities.getWindowAncestor(this).dispose();
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
                Labels.create().text("xp"),
                buildXp())
        );
        panel.add(buildProblem());
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(buildBoxChallenges("Parâmetros"));
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
        problemButton = new JButton("Narrativa");
        problemButton.setIcon(IconFactory.get().create("fa:font"));
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
            parameterButton.setIcon(IconFactory.get().create("fa:plus"));
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
        componentItems.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
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
        panelItems = new JPanel();
        panelItems.setLayout(new GridLayout(0, 1));
        challenge.getInput().forEach((item) -> {
            panelItems.add(buildChallengesItem(item));
        });
        return panelItems;
    }

    /**
     * Builds the challenge items
     *
     * @param file
     * @return JComponent
     */
    private JComponent buildChallengesItem(MissionValue file) {
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
        component.setPreferredSize(new Dimension(0, 90));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        try {
            component.add(new JLabel(getIcon(file)), BorderLayout.WEST);
        } catch (IOException ex) {
            ExceptionHandler.get().handle(ex);
        }
        component.add(buttons, BorderLayout.EAST);
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component, BorderLayout.NORTH);
        return itemComponent;

    }

    /**
     * Returns the user image icon
     *
     * @return ImageIcon
     */
    private ImageIcon getIcon(MissionValue value) throws IOException {
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
     * Mission parameter panel
     */
    private class MissionParameterPanel extends JComponent {

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
        private MissionParameterPanel() {
            initGui();
            initEvents();
        }

        /**
         * Initializes the interface
         */
        private void initGui() {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(300, 200));
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
            JButton button = Buttons.create("Ok").onClick((ev) -> {
                if (inputFile == null) {
                    JOptionPane.showMessageDialog(null, "Input is required");
                    return;
                }
                if (outputFile == null && outputText.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ouput is required");
                    return;
                }
                challenge.addInput(new MissionValue(inputFile));
                challenge.addOutput(buildMissionValue());
                SwingUtilities.getWindowAncestor(this).dispose();
            });
            button.setIcon(IconFactory.get().create("fa:check"));
            panel.add(button);
            return panel;
        }

        /**
         * builds the mission value
         *
         * @return MissionValue
         */
        private MissionValue buildMissionValue() {
            if (!outputText.getText().isEmpty()) {
                return new MissionValue(
                        MissionValueType.TEXT,
                        outputText.getText()
                );
            }
            return new MissionValue(outputFile);
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
            inputButton.setIcon(IconFactory.get().create("fa:sign-in"));
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(inputButton, BorderLayout.WEST);
            return panel;
        }

        /**
         * Builds the output component
         *
         * @return JComponent
         */
        private JComponent buildOutput() {
            outputButton = new JButton("Output");
            outputButton.setIcon(IconFactory.get().create("fa:sign-out"));
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(outputButton, BorderLayout.WEST);
            return panel;
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
     * Mission parameter panel
     */
    private class MissionProblemPanel extends JComponent {

        /** The code editor */
        private CodeEditor textArea;

        public MissionProblemPanel() {
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
