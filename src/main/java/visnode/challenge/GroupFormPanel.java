package visnode.challenge;

import com.github.rxsling.Buttons;
import com.github.rxsling.Inputs;
import com.github.rxsling.Labels;
import com.github.rxsling.Panel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import visnode.application.ExceptionHandler;
import visnode.application.Messages;
import visnode.application.ProcessTransferHandler;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;
import visnode.gui.ScrollFactory;
import visnode.gui.UIHelper;
import visnode.repository.GroupRepository;
import visnode.repository.RepositoryException;
import visnode.repository.UserRepository;
import visnode.user.User;

/**
 *
 */
public class GroupFormPanel extends JPanel {

    /** Group */
    private final Group group;
    /** Users */
    private final List<User> users;
    /** user list */
    private JList<User> list;
    /** Name */
    private JTextField name;

    public GroupFormPanel(Group group) {
        super();
        this.group = group;
        this.users = loadUsers();
        initGui();
        initEvents();
    }

    /**
     * Load the users
     *
     * @return {@code List<User>}
     */
    private List<User> loadUsers() {
        try {
            return UserRepository.get().getAll();
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return new ArrayList<>();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        showDialog(new Group());
    }
   
    /**
     * Shows the dialog
     * 
     * @param group
     */
    public static void showDialog(Group group) {
        WindowFactory.modal().title("New group").create((container) -> {
            container.add(new GroupFormPanel(group));
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));
        add(buildForm());
        add(buildButtons(), BorderLayout.SOUTH);
        updateList();
        List<Integer> listIndex = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            final int index = i;
            if (group.getUsers().stream()
                    .filter((user) -> user.getUser().getId() == users.get(index).getId())
                    .findFirst().isPresent()) {
                listIndex.add(index);
            }
        }
        list.setSelectedIndices(listIndex.stream().mapToInt((i) -> i).toArray());
    }

    /**
     * Initializes the events
     */
    private void initEvents() {

    }

    /**
     * Updates the list
     */
    private void updateList() {
        updateList(null);
    }

    /**
     * Updates the list based on a filter
     *
     * @param filter
     */
    private void updateList(String filter) {
        if (list == null) {
            return;
        }
        final String newFilter = (filter != null) ? filter.toLowerCase() : null;
        DefaultListModel<User> model = new DefaultListModel();
        users.stream()
                .filter((user) -> newFilter == null || user.getName().toLowerCase().contains(newFilter))
                .forEach((user) -> {
                    model.addElement(user);
                });
        list.setModel(model);
    }

    /**
     * Builds the form
     * 
     * @return JComponent
     */
    private JComponent buildForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(buildItem(
                Labels.create().text(Messages.get().message("challenge.name")),
                buildName())
        );
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(buildUser());
        return ScrollFactory.pane(container).create();
    }
    
    /**
     * Builds the user
     * 
     * @return 
     */
    private JComponent buildUser() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(buildUserFilter(), BorderLayout.NORTH);
        panel.add(buildUserList());
        return panel;
    }

    /**
     * Builds the user filter
     * 
     * @return JComponent
     */
    private JComponent buildUserFilter() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(Labels.create(IconFactory.get().create("fa:search")), BorderLayout.WEST);
        panel.add(Inputs.text()
                .preferredSize(new Dimension(100, 25))
                .subscribeValue((val) -> updateList(val)));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 3));
        return panel;
    }

    /**
     * Builds the user list
     * 
     * @return JComponent
     */
    private JComponent buildUserList() {
        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setCellRenderer(new CellRenderer(list.getCellRenderer()));
        list.setTransferHandler(new ProcessTransferHandler());
        list.setDragEnabled(true);
        list.setDropMode(DropMode.ON_OR_INSERT);
        return ScrollFactory.pane(list).create();
    }

    /**
     * Builds the name field
     *
     * @return JComponent
     */
    private JComponent buildName() {
        name = new JTextField();
        name.setText(group.getName());
        return name;
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
     * Builds the button panel
     *
     * @return JComponent
     */
    private JComponent buildButtons() {
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton button = Buttons.create("Create").onClick((ev) -> {
            try {
                group.setName(name.getText());
                group.clearUsers();
                list.getSelectedValuesList().forEach((item) -> {
                    group.addUser(item);
                });
                if (group.getName().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name is required");
                    return;
                }
                if (!group.hasUsers()) {
                    JOptionPane.showMessageDialog(null, "Users are required");
                    return;
                }
                if (group.getId() == 0) {
                    GroupRepository.get().save(group);
                } else {
                    GroupRepository.get().update(group);
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
            JLabel description = (JLabel) defaultRenderer.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
            description.setText(value.getName());
            description.setIcon(IconFactory.get().create("fa:plus"));
            description.setBorder(BorderFactory.createEmptyBorder(1, 10, 3, 3));
            // Builds the component
            JPanel component = new JPanel();
            component.setLayout(new FlowLayout());
            component.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
            if (isSelected) {
                component.setBackground(UIHelper.getColor("ProcessBrowser.odd"));
            } else {
                if (index % 2 == 0) {
                    component.setBackground(UIHelper.getColor("ProcessBrowser.even"));
                }
            }
            component.add(description);
            component.setOpaque(true);
            return component;
        }

    }
}
