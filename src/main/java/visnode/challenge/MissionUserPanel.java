package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
import visnode.repository.RepositoryException;
import visnode.repository.UserRepository;
import visnode.user.User;

/**
 * The challenge user panel
 */
public class MissionUserPanel extends JPanel {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 64;

    /**
     * Creates a new mission list panel
     */
    private MissionUserPanel() {
        super();
        initGui();
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
        JComponent containerItems = new JPanel();
        containerItems.setLayout(new BorderLayout());
        containerItems.add(buildListComponent());
        JScrollPane scrollPane = ScrollFactory.pane(containerItems).create();
        scrollPane.setBorder(null);
        return scrollPane;
    }

    /**
     * Returns the list component
     *
     * @return JComponent
     */
    private JComponent buildListComponent() {
        try {
            JPanel list = new JPanel();
            list.setLayout(new GridLayout(0, 1));
            UserRepository.get().getAll().forEach((user) -> {
                list.add(buildListItem(user));
            });
            JPanel panelItems = new JPanel();
            panelItems.setLayout(new BorderLayout());
            panelItems.add(list, BorderLayout.NORTH);
            return panelItems;
        } catch (RepositoryException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return new JPanel();
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
        JPanel imagePanel = new JPanel();
        imagePanel.add(new JLabel(new ImageIcon(image)));
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
