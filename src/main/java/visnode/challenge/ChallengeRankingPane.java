package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.commons.ImageScale;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ListItemComponent;
import visnode.gui.ScrollFactory;
import visnode.gui.UIHelper;
import visnode.repository.GroupRepository;
import visnode.repository.RepositoryException;
import visnode.repository.UserRepository;
import visnode.user.User;
import visnode.user.UserController;

/**
 * The challenge ranking panel
 */
public class ChallengeRankingPane extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 64;
    /** User list */
    private List<User> users;

    /**
     * Creates a new challenge list panel
     */
    private ChallengeRankingPane() {
        super();
        loadUsers();
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
        add(buildTabs());
        add(buildButtons(), BorderLayout.SOUTH);
    }

    private void loadUsers() {
        this.users = new ArrayList<>();
        try {
            this.users = UserRepository.get().getAll().stream().
                    sorted((it, it2) -> {
                        return it.getXp() > it2.getXp() ? -1 : 1;
                    }).collect(Collectors.toList());
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
    }

    /**
     * Builds user tabs
     *
     * @return JComponent
     */
    private JComponent buildTabs() {
        Map<String, List<User>> map = buildGroups();
        JTabbedPane tabs = new JTabbedPane();
        map.forEach((key, values) -> {
            tabs.add(key, new Item(values));
        });
        tabs.add("General", new Item(users));
        return tabs;
    }

    /**
     * Builds the groups
     *
     * @return {@code Map<String, List<User>>}
     */
    private Map<String, List<User>> buildGroups() {
        Map<String, List<User>> map = new TreeMap<>();
        User user = UserController.get().getUser();
        try {
            GroupRepository.get().getAll().stream()
                    .filter((group)
                            -> group.getUsers().stream()
                            .filter((it) -> it.getUser().getId() == user.getId())
                            .findFirst().isPresent()
                    )
                    .forEach((group) -> {
                        map.put(
                                group.getName(),
                                group.getUsers().stream()
                                        .map((it) -> it.getUser())
                                        .sorted((it, it2) -> {
                                            return it.getXp() > it2.getXp() ? -1 : 1;
                                        })
                                        .collect(Collectors.toList())
                        );
                    });
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return map;
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

    private class Item extends JPanel {

        /** User list */
        private final List<User> items;
        /** Challenge list */
        private JList<User> list;

        /**
         * Creates a new challenge list panel
         */
        private Item(List<User> items) {
            super();
            this.items = items;
            initGui();
        }

        /**
         * Initializes the interface
         */
        private void initGui() {
            setLayout(new BorderLayout());
            add(buildList());
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
            items.forEach((obj) -> {
                model.addElement(obj);
            });
            list.setModel(model);
            JScrollPane scrollPane = ScrollFactory.pane(list).create();
            scrollPane.setBorder(null);
            return scrollPane;
        }

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
            itemComponent.add(component, BorderLayout.WEST);
            return itemComponent;
        }

    }
}
