package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

/**
 * The challenge user panel
 */
public class MissionUserPanel extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 64;
    /** Group */
    private JComboBox<Group> groups;
    /** Button filter */
    private JButton buttonFilter;
    /** Button update */
    private JButton buttonUpdate;
    /** Button new group */
    private JButton buttonNewGroup;
    /** Items list */
    private JPanel items;
    /** Container */
    private JPanel container;
    /** Users */
    private List<User> users;

    /**
     * Creates a new mission list panel
     */
    private MissionUserPanel() {
        super();
        loadUsers();
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        Messages.get().message("user").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new MissionUserPanel());
            }).setVisible(true);
        });
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildGroup(), BorderLayout.NORTH);
        add(buildList());
        add(buildButtons(), BorderLayout.SOUTH);
        loadGroups();
        loadUsersList();
    }

    /**
     * Load users
     */
    private void loadUsers() {
        this.users = new ArrayList<>();
        try {
            this.users = UserRepository.get().getAll();
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
    }

    /**
     * Load groups
     */
    private void loadGroups() {
        Object selected = groups.getSelectedItem();
        groups.removeAllItems();
        groups.addItem(new Group("All"));
        try {
            GroupRepository.get().getAll().forEach((it) -> {
                groups.addItem(it);
            });
            if (selected == null) {
                groups.setSelectedIndex(0);
            } else {
                groups.setSelectedItem(selected);
            }
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
    }

    /**
     * Load user list
     */
    private void loadUsersList() {
        if (items != null) {
            container.remove(items);
        }
        container.add(buildListComponent());
        container.revalidate();
        repaint();
    }

    /**
     * Initializes the events
     */
    private void initEvents() {
        buttonNewGroup.addActionListener((evt) -> {
            GroupFormPanel.showDialog();
            loadGroups();
        });
        buttonUpdate.addActionListener((evt) -> {
            Group group = (Group) groups.getSelectedItem();
            if (group == null || group.getId() == 0) {
                JOptionPane.showMessageDialog(null, "No group selected");
                return;
            }
            GroupFormPanel.showDialog(group);
            loadUsersList();
        });
        buttonFilter.addActionListener((evt) -> {
            loadUsersList();
        });
    }

    /**
     * Builds the group box
     *
     * @return JComponent
     */
    private JComponent buildGroup() {
        // Builds the label
        JLabel label = new JLabel();
        Messages.get().message("challenge.group").subscribe((msg) -> {
            label.setText(msg);
        }).dispose();
        // Build de group combo-box
        groups = new JComboBox();
        // Action filter
        buttonFilter = new JButton(IconFactory.get().create("fa:search"));
        // Action update
        buttonUpdate = new JButton(IconFactory.get().create("fa:pencil"));
        // Action new group
        buttonNewGroup = new JButton();
        Messages.get().message("challenge.newGroup").subscribe((msg) -> {
            buttonNewGroup.setText(msg);
            buttonNewGroup.setIcon(IconFactory.get().create("fa:plus"));
        });
        // Builds the box
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(groups);
        panel.add(buttonFilter);
        panel.add(buttonUpdate);
        panel.add(buttonNewGroup);
        return panel;
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
            SwingUtilities.getWindowAncestor(MissionUserPanel.this).dispose();
        });
        JComponent panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        return panel;
    }

    /**
     * Creates the mission list
     *
     * @return JComponent
     */
    private JComponent buildList() {
        container = new JPanel();
        container.setLayout(new BorderLayout());
        JScrollPane scrollPane = ScrollFactory.pane(container).create();
        scrollPane.setBorder(null);
        return scrollPane;
    }

    /**
     * Returns the list component
     *
     * @return JComponent
     */
    private JComponent buildListComponent() {
        JPanel list = new JPanel();
        list.setLayout(new GridLayout(0, 1));
        List<User> userList = users;
        Group group = (Group) groups.getSelectedItem();
        if (group != null && group.getId() > 0) {
            userList = users.stream().filter((user) -> {
                return group.getUsers().stream()
                        .filter((it) -> it.getUser().getId() == user.getId())
                        .findFirst().isPresent();
            }).collect(Collectors.toList());
        }
        userList.forEach((user) -> {
            list.add(buildListItem(user));
        });
        items = new JPanel();
        items.setLayout(new BorderLayout());
        items.add(list, BorderLayout.NORTH);
        return items;
    }

    /**
     * Creates the user list item
     *
     * @return JComponent
     */
    private JComponent buildListItem(User user) {
        // Solve challenge
        JButton solve = new JButton();
        Messages.get().message("challenge").subscribe((msg) -> {
            solve.setIcon(IconFactory.get().create("fa:filter"));
            solve.setText(msg);
        });
        solve.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(MissionUserPanel.this).dispose();
            MissionUserChallengePanel.showDialog(user);
        });
        // Conquests challenge
        JButton conquest = new JButton();
        Messages.get().message("challenge.conquest").subscribe((msg) -> {
            conquest.setIcon(IconFactory.get().create("fa:dollar"));
            conquest.setText(msg);
        });
        conquest.addActionListener((ev) -> {
            ChallengeConquestPanel.showDialog(user);
        });
        // Buttons
        GridLayout buttonsActionsLayout = new GridLayout(2, 1);
        buttonsActionsLayout.setVgap(5);
        JPanel buttonsActions = new JPanel();
        buttonsActions.setLayout(buttonsActionsLayout);
        buttonsActions.add(solve);
        buttonsActions.add(conquest);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(buttonsActions, BorderLayout.NORTH);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
        // Image
        BufferedImage image = ImageScale.scale(user.getImageBuffered(), THUMBNAIL_SIZE);
        JLabel icon = new JLabel(new ImageIcon(image));
        icon.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
        JPanel imagePanel = new JPanel();
        imagePanel.add(icon);
        // Name
        JLabel name = new JLabel();
        name.setText(user.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Xp
        JLabel xp = new JLabel(String.format("%s xp", user.getXp()));
        xp.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        // Header
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        header.add(name);
        // Container
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(2, 1));
        container.add(header);
        container.add(xp);
        // Builds the component
        JPanel component = new JPanel();
        component.setLayout(new FlowLayout());
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        component.add(imagePanel);
        component.add(container);
        component.add(container);
        ListItemComponent itemComponent = new ListItemComponent();
        itemComponent.add(component, BorderLayout.WEST);
        itemComponent.add(buttonsPanel, BorderLayout.EAST);
        return itemComponent;

    }

}
