package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import visnode.repository.RepositoryException;
import visnode.repository.UserRepository;
import visnode.user.User;

/**
 * The challenge ranking panel
 */
public class ChallengeRankingPane extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 64;
    
    /** Challenge list */
    private JList<User> list;

    /**
     * Creates a new challenge list panel
     */
    private ChallengeRankingPane() {
        super();
        initGui();
    }

    /**
     * Shows the dialog
     *
     */
    public static void showDialog() {
        WindowFactory.modal().title("Ranking").create((container) -> {
            container.setBorder(null);
            container.add(new ChallengeRankingPane());
        }).setVisible(true);
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
            SwingUtilities.getWindowAncestor(ChallengeRankingPane.this).dispose();
        });
        JComponent panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        return panel;
    }    
    
    /**
     * Creates the challenge list
     *
     * @return JComponent
     */
    private JComponent buildList() {
        list = new JList<>();
        list.setCellRenderer(new CellRenderer(list.getCellRenderer()));
        DefaultListModel<User> model = new DefaultListModel();
        try {
            UserRepository.get().getAll().stream().
                    sorted((it, it2) -> {
                        return it.getXp() > it2.getXp() ? -1 : 1;
                    }).
                    forEach((obj) -> {
                        model.addElement(obj);
                    });
        } catch (RepositoryException ex) {
            Logger.getLogger(ChallengeRankingPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        list.setModel(model);
        JScrollPane scrollPane = ScrollFactory.pane(list).create();
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private class CellRenderer implements ListCellRenderer<User> {

        /** Renderer to base background and foreground color */
        private final ListCellRenderer defaultRenderer;

        /**
         * Creates
         *
         * @param defaultRenderer
         */
        public CellRenderer(ListCellRenderer defaultRenderer) {
            this.defaultRenderer = defaultRenderer;
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected, boolean cellHasFocus) {
            BufferedImage image = ImageScale.scale(value.getImageBuffered(), THUMBNAIL_SIZE);
            JLabel icon = new JLabel(new ImageIcon(image));
            icon.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
            JPanel imagePanel = new JPanel();
            imagePanel.add(icon); 
            // Position
            JLabel position = new JLabel();
            position.setText(String.format("%sº", index + 1));
            position.setFont(new Font("Segoe UI", Font.BOLD, 18));
            // Name
            JLabel name = new JLabel();
            name.setText(value.getName());
            name.setFont(new Font("Segoe UI", Font.BOLD, 18));
            // Xp
            JLabel xp = new JLabel(String.format("%s xp", value.getXp()));
            xp.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            // Header
            JPanel header = new JPanel();
            header.setLayout(new FlowLayout());
            header.add(position);
            header.add(name);            
            // Container
            JPanel container = new JPanel();
            container.setLayout(new GridLayout(2, 1));
            container.add(header);
            container.add(xp);
            // Builds the component4
            JPanel component = new JPanel();
            component.setLayout(new FlowLayout());
            component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            component.add(imagePanel);
            component.add(container);
            ListItemComponent itemComponent = new ListItemComponent();
            itemComponent.add(component,  BorderLayout.WEST);
            return itemComponent;
        }

    }
}
