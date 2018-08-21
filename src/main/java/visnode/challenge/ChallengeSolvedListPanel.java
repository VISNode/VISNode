package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.commons.ImageScale;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.gui.UIHelper;
import visnode.repository.MissionUserRepository;
import visnode.repository.RepositoryException;

/**
 * The challenge solved list panel
 */
public class ChallengeSolvedListPanel extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 64;

    /** Challenge list */
    private JList<MissionUser> list;
    /** Challenge */
    private final Mission mission;

    /**
     * Creates a new challenge list panel
     */
    private ChallengeSolvedListPanel(Mission mission) {
        super();
        this.mission = mission;
        initGui();
    }

    /**
     * Shows the dialog
     *
     * @param mission
     */
    public static void showDialog(Mission mission) {
        Messages.get().message("challenge.solutions").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new ChallengeSolvedListPanel(mission));
            }).setVisible(true);
        }).dispose();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildList());
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Creates the challenge list
     *
     * @return JComponent
     */
    private JComponent buildList() {
        list = new JList<>();
        list.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.getWindowAncestor(ChallengeSolvedListPanel.this).dispose();
                MissionUser missionUser = list.getSelectedValue();              
                ChallengeController.get().openProject(missionUser.getSubmission(), mission);
            }

        });
        list.setCellRenderer(new CellRenderer());
        DefaultListModel<MissionUser> model = new DefaultListModel();
        try {
            MissionUserRepository.get().get(mission.getId()).stream().
                    sorted((a, b) -> b.getXp() - a.getXp()).
                    forEach((obj) -> {
                        model.addElement(obj);
                    });
        } catch (RepositoryException ex) {
            Logger.getLogger(ChallengeSolvedListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        list.setModel(model);
        JScrollPane scrollPane = ScrollFactory.pane(list).create();
        scrollPane.setBorder(null);
        return scrollPane;
    }
    
    /**
     * Build the buttons
     *
     * @return JComponent
     */
    private JComponent buildButtons() {
        JButton button = new JButton();
        Messages.get().message("next").subscribe((msg) -> {
            button.setText(msg);
            button.setIcon(IconFactory.get().create("fa:check"));
        });
        button.addActionListener((evt) -> {
            SwingUtilities.getWindowAncestor(ChallengeSolvedListPanel.this).dispose();
        });
        JComponent panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
    
    private class CellRenderer implements ListCellRenderer<MissionUser> {

        @Override
        public Component getListCellRendererComponent(JList<? extends MissionUser> list, MissionUser value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel imagePanel = new JPanel();
            BufferedImage image = ImageScale.scale(value.getUser().getImageBuffered(), THUMBNAIL_SIZE);
            JLabel icon = new JLabel(new ImageIcon(image));
            icon.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
            imagePanel.add(icon);
            // Title label
            JPanel info = new JPanel();
            info.setLayout(new GridLayout(0, 1));
            info.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            JLabel label = new JLabel();
            label.setText(value.getUser().getName());
            label.setFont(new Font("Segoe UI", Font.BOLD, 18));
            info.add(label);
            info.add(new JLabel(String.format("xp: %s", value.getXp())));
            info.add(new JLabel(String.format("Data: %s", value.getDateFinalFormat())));
            // Buttons
            JPanel buttons = new JPanel();
            JButton open = new JButton();
            Messages.get().message("open").subscribe((msg) -> {
                open.setText(msg);
                open.setIcon(IconFactory.get().create("fa:folder-open"));
            });
            buttons.add(open);
            // Builds the component
            JPanel component = new JPanel();
            component.setLayout(new BorderLayout());
            component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            component.add(imagePanel, BorderLayout.WEST);
            component.add(info);
            component.add(buttons, BorderLayout.EAST);
            ListItemComponent itemComponent = new ListItemComponent();
            itemComponent.add(component);
            return itemComponent;
        }

    }
}
