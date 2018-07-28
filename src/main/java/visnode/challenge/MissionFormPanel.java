package visnode.challenge;

import com.github.rxsling.Buttons;
import com.github.rxsling.Labels;
import com.github.rxsling.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import visnode.application.Messages;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.gui.UIHelper;
import visnode.repository.MissionRepository;
import visnode.repository.RepositoryException;

/**
 * New mission panel
 */
public class MissionFormPanel extends JPanel {

    /** Name */
    private JTextField name;
    /** Description */
    private JTextArea description;
    /** Add challenge button */
    private JButton addChallenge;
    /** Mission */
    private final Mission mission;
    /** Panel items */
    private JComponent panelItems;
    /** Component items */
    private JComponent componentItems;
    /** Box component */
    private JComponent boxComponent;

    /**
     * Creates a mission
     */
    private MissionFormPanel(Mission mission) {
        this.mission = mission;
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        showDialog(new Mission());
    }

    /**
     * Shows the dialog
     *
     * @param mission
     */
    public static void showDialog(Mission mission) {
        WindowFactory.modal().title("New mission").create((container) -> {
            container.add(new MissionFormPanel(mission));
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
        addChallenge.addActionListener((ev) -> {
            Challenge challenge = new Challenge();
            challenge.setLevel(mission.size() + 1);
            Challenge edited = MissionChallengeFormPanel.showDialog(challenge);
            if (edited != null) {
                mission.addChallange(challenge);
                reloadItems();
            }
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
                mission.setName(name.getText());
                mission.setDescription(description.getText());
                if (mission.getName().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name is required");
                    return;
                }
                if (mission.getDescription().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description is required");
                    return;
                }
                if (mission.getChallenges().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Challenges are required");
                    return;
                }
                if (mission.getId() == 0) {
                    MissionRepository.get().save(mission);
                } else {
                    MissionRepository.get().update(mission);
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
        addChallenge = new JButton();
        Messages.get().message("challenge.new").subscribe((msg) -> {
            addChallenge.setText(msg);
            addChallenge.setIcon(IconFactory.get().create("fa:plus"));
        });
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        buttons.add(addChallenge, BorderLayout.WEST);
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
        mission.getChallenges().forEach((challenge) -> {
            panelItems.add(buildChallengesItem(challenge));
        });
        return panelItems;
    }

    /**
     * Builds the box challenges items
     *
     * @return JComponent
     */
    private JComponent buildChallengesItem(Challenge challenge) {
        JButton delete = new JButton();
        delete.setIcon(IconFactory.get().create("fa:trash-o"));
        delete.addActionListener((ev) -> {
            mission.removeChallenge(challenge);
            reloadItems();
        });
        JButton edit = new JButton();
        edit.setIcon(IconFactory.get().create("fa:pencil"));
        edit.addActionListener((ev) -> {
            MissionChallengeFormPanel.showDialog(challenge);
        });
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(delete);
        buttons.add(edit);
        JPanel component = new JPanel();
        component.setLayout(new BorderLayout());
        component.setPreferredSize(new Dimension(0, 40));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(new JLabel("Level: " + String.valueOf(challenge.getLevel())));
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
        name.setText(mission.getName());
        return name;
    }

    /**
     * Builds the description field
     *
     * @return JComponent
     */
    private JComponent buildDescription() {
        description = new JTextArea(5, 20);
        description.setText(mission.getDescription());
        return new JScrollPane(description);
    }

}
