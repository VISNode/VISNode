package visnode.challenge;

import visnode.repository.ChallengeUserRepository;
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
import javax.swing.BoxLayout;
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
import visnode.repository.RepositoryException;

/**
 * The challenge solved list panel
 */
public class ChallengeSolvedListPanel extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 64;

    /** Challenge list */
    private JList<ChallengeUser> list;
    /** Challenge */
    private final int challenge;

    /**
     * Creates a new challenge list panel
     */
    private ChallengeSolvedListPanel(int challenge) {
        super();
        this.challenge = challenge;
        initGui();
    }

    /**
     * Shows the dialog
     *
     * @param challenge
     */
    public static void showDialog(int challenge) {
        WindowFactory.modal().title("Soluções").create((container) -> {
            container.setBorder(null);
            container.add(new ChallengeSolvedListPanel(challenge));
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
                SwingUtilities.getWindowAncestor(ChallengeSolvedListPanel.this).dispose();
                ChallengeUser challenge = list.getSelectedValue();
                VISNode.get().getController().open(challenge.getSubmission());
            }

        });
        list.setCellRenderer(new CellRenderer());
        DefaultListModel<ChallengeUser> model = new DefaultListModel();
        try {
            ChallengeUserRepository.get().get(challenge).stream().
                    sorted((a, b) -> b.getXp() - a.getXp()).
                    forEach((obj) -> {
                        model.addElement(obj);
                    });
        } catch (RepositoryException ex) {
            Logger.getLogger(ChallengeSolvedListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        list.setModel(model);
        return ScrollFactory.pane(list).create();
    }

    private class CellRenderer implements ListCellRenderer<ChallengeUser> {

        @Override
        public Component getListCellRendererComponent(JList<? extends ChallengeUser> list, ChallengeUser value, int index, boolean isSelected, boolean cellHasFocus) {
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
