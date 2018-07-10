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
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.commons.ImageScale;
import visnode.commons.swing.WindowFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.repository.MissionUserRepository;
import visnode.repository.RepositoryException;

/**
 * The challenge solved list panel
 */
public class MissionSolvedListPanel extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 64;

    /** Mission list */
    private JList<MissionUser> list;
    /** Mission */
    private final Mission mission;

    /**
     * Creates a new challenge list panel
     */
    private MissionSolvedListPanel(Mission mission) {
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
        WindowFactory.modal().title("Soluções").create((container) -> {
            container.setBorder(null);
            container.add(new MissionSolvedListPanel(mission));
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildList());
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
                SwingUtilities.getWindowAncestor(MissionSolvedListPanel.this).dispose();
                MissionUser mission = list.getSelectedValue();
                VISNode.get().getController().open(mission.getSubmission());
            }

        });
        list.setCellRenderer(new CellRenderer());
        DefaultListModel<MissionUser> model = new DefaultListModel();
        try {
            MissionUserRepository.get().get(mission).stream().
                    sorted((a, b) -> b.getXp() - a.getXp()).
                    forEach((obj) -> {
                        model.addElement(obj);
                    });
        } catch (RepositoryException ex) {
            Logger.getLogger(MissionSolvedListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        list.setModel(model);
        return ScrollFactory.pane(list).create();
    }

    private class CellRenderer implements ListCellRenderer<MissionUser> {

        @Override
        public Component getListCellRendererComponent(JList<? extends MissionUser> list, MissionUser value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel imagePanel = new JPanel();
            BufferedImage image = ImageScale.scale(value.getUser().getImageBuffered(), THUMBNAIL_SIZE);
            imagePanel.add(new JLabel(new ImageIcon(image)));
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
