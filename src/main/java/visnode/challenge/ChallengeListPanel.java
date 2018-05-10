package visnode.challenge;

import visnode.repository.ChallengeUserRepository;
import visnode.repository.ChallengeRepository;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.commons.MultiFileInput;
import visnode.commons.swing.WindowFactory;
import visnode.gui.ScrollFactory;
import visnode.gui.UIHelper;
import visnode.user.UserController;

/**
 * The challenge list panel
 */
public class ChallengeListPanel extends JPanel {

    /** Challenge repository */
    private final ChallengeRepository repository;
    /** Challenge list */
    private JList<Challenge> list;

    /**
     * Creates a new challenge list panel
     */
    private ChallengeListPanel() {
        super();
        this.repository = new ChallengeRepository();
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        Messages.get().message("challenge").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new ChallengeListPanel());
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
                SwingUtilities.getWindowAncestor(ChallengeListPanel.this).dispose();
                Challenge challenge = list.getSelectedValue();
                if (solved(challenge)) {
                    ChallengeSolvedListPanel.showDialog(challenge.getId());
                    return;
                }
                VISNode.get().getController().createNew();
                ChallengeController.get().start(challenge);
                VISNode.get().getModel().getNetwork().setInput(new MultiFileInput(new File(challenge.getInput())));
                ChallengeProblemPanel.showDialog();
            }

        });
        list.setCellRenderer(new CellRenderer(list.getCellRenderer()));
        DefaultListModel<Challenge> model = new DefaultListModel();
        repository.getChallenges().forEach((challenge) -> {
            model.addElement(challenge);
        });
        list.setModel(model);
        return ScrollFactory.pane(list).create();
    }

    /**
     * Returns true if the challenge has solved
     *
     * @param value
     * @return boolean
     */
    private boolean solved(Challenge value) {
        return ChallengeUserRepository.get().has(UserController.get().getUser(), value.getId());
    }

    private class CellRenderer implements ListCellRenderer<Challenge> {

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
        public Component getListCellRendererComponent(JList<? extends Challenge> list, Challenge value, int index, boolean isSelected, boolean cellHasFocus) {
            // Title label
            JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
            label.setText(value.getName());
            label.setFont(new Font("Segoe UI", Font.BOLD, 18));
            // Description label
            JLabel description = new JLabel(value.getDescription());
            description.setForeground(description.getForeground());
            description.setBorder(BorderFactory.createEmptyBorder(1, 10, 3, 3));
            description.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            // Solve challenge
            JButton solve = new JButton();
            Messages.get().message("challenge.solve").subscribe((msg) -> {
                solve.setText(msg);
            });
            JButton solutions = new JButton();
            Messages.get().message("challenge.solutions").subscribe((msg) -> {
                solutions.setText(msg);
            });
            // Builds the component
            JPanel componentBorder = new JPanel();
            componentBorder.setLayout(new BorderLayout());
            componentBorder.setBorder(BorderFactory.createLineBorder(UIHelper.getColor("Node.border")));
            JPanel componentBox = new JPanel();
            componentBox.setLayout(new BorderLayout());
            componentBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JPanel component = new JPanel();
            component.setLayout(new BorderLayout());
            component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            component.add(label);
            component.add(description, BorderLayout.SOUTH);
            if (solved(value)) {
                component.add(solutions, BorderLayout.EAST);
            } else {
                component.add(solve, BorderLayout.EAST);
            }
            componentBorder.add(component);
            componentBox.add(componentBorder);
            return componentBox;
        }

    }
}
