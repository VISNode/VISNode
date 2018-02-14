package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import visnode.commons.swing.WindowFactory;
import visnode.gui.ScrollFactory;
import visnode.gui.UIHelper;
import visnode.user.UserController;

/**
 * The challenge solved list panel
 */
public class ChallengeSolvedListPanel extends JPanel {

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
        Messages.get().message("challenge").subscribe((msg) -> {
            WindowFactory.modal().title(msg).create((container) -> {
                container.setBorder(null);
                container.add(new ChallengeSolvedListPanel(challenge));
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
                SwingUtilities.getWindowAncestor(ChallengeSolvedListPanel.this).dispose();
                ChallengeUser challenge = list.getSelectedValue();
                VISNode.get().getController().open(challenge.getSubmission());
            }

        });
        list.setCellRenderer(new CellRenderer(list.getCellRenderer()));
        DefaultListModel<ChallengeUser> model = new DefaultListModel();
        ChallengeUserRepository.get().get(challenge).forEach((obj) -> {
            model.addElement(obj);
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

    private class CellRenderer implements ListCellRenderer<ChallengeUser> {

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
        public Component getListCellRendererComponent(JList<? extends ChallengeUser> list, ChallengeUser value, int index, boolean isSelected, boolean cellHasFocus) {
            // Title label
            JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
            label.setText(value.getUser());
            label.setFont(new Font("Segoe UI", Font.BOLD, 18));
            JButton open = new JButton();
            Messages.get().message("open").subscribe((msg) -> {
                open.setText(msg);
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
            component.add(open, BorderLayout.EAST);
            componentBorder.add(component);
            componentBox.add(componentBorder);
            return componentBox;
        }

    }
}
