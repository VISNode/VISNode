package visnode.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import com.github.rxsling.Inputs;
import visnode.gui.IconFactory;
import visnode.gui.ProcessInformationPane;
import visnode.gui.ScrollFactory;
import visnode.pdi.Process;
import visnode.pdi.process.ProcessLoader;

/**
 * Process browser
 */
public class ProcessBrowser extends JComponent {

    /** Process list */
    private JList<Class<Process>> list;
    /** Process meta-data */
    private final Map<Class<Process>, ProcessMetadata> processMetadata;

    /**
     * Creates the process browser
     */
    public ProcessBrowser() {
        super();
        this.processMetadata = new HashMap<>();
        initGui();
        buildProcessMetadata();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(buildFilterPanel(), BorderLayout.NORTH);
        add(buildList());
    }

    /**
     * Builds the process meta-data
     */
    private void buildProcessMetadata() {
        VISNode.get().getModel().getUserPreferences().getLocaleSubject().subscribe((locale) -> {
            processMetadata.clear();
            ProcessLoader.get().getProcesses().forEach((process) -> {
                processMetadata.put((Class<Process>) process, ProcessMetadata.fromClass(process, locale));
            });
            updateList();
        });
    }

    /**
     * Builds the filter panel
     *
     * @return JComponent
     */
    private JComponent buildFilterPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(new JLabel(IconFactory.get().create("fa:search")), BorderLayout.WEST);
        panel.add(Inputs.text()
                .preferredSize(new Dimension(100, 25))
                .subscribeValue((val) -> updateList(val)));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 3));
        return panel;
    }

    /**
     * Creates the process list
     *
     * @return JComponent
     */
    private JComponent buildList() {
        list = new JList<>();
        list.setCellRenderer(new CellRenderer(list.getCellRenderer()));
        list.setTransferHandler(new ProcessTransferHandler());
        list.setDragEnabled(true);
        list.setDropMode(DropMode.ON_OR_INSERT);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() % 2 == 0) {
                    ProcessInformationPane.showDialog(list.getSelectedValue());
                }
            }
        });
        return ScrollFactory.pane(list).create();
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
        if (filter != null) {
            filter = filter.toLowerCase();
        }
        TreeMap<String, Class<Process>> orderMap = new TreeMap<>();
        for (Class process : ProcessLoader.get().getProcesses()) {
            ProcessMetadata metadata = processMetadata.get(process);
            if (metadata == null) {
                break;
            }
            if (filter != null) {
                if (!metadata.containsName(filter) && !metadata.containsDescription(filter)) {
                    continue;
                }
            }
            orderMap.put(metadata.getName(), process);
        }
        DefaultListModel<Class<Process>> model = new DefaultListModel();
        orderMap.values().forEach((it) -> {
            model.addElement(it);
        });
        list.setModel(model);
    }

    private class CellRenderer implements ListCellRenderer<Class<Process>> {

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
        public Component getListCellRendererComponent(JList<? extends Class<Process>> list, Class<Process> value, int index, boolean isSelected, boolean cellHasFocus) {
            ProcessMetadata metadata = processMetadata.get(value);
            // Title label
            JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
            label.setText(metadata.getName());
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            // Description label
            JLabel description = new JLabel(metadata.getDescription());
            description.setForeground(description.getForeground());
            description.setBorder(BorderFactory.createEmptyBorder(1, 10, 3, 3));
            description.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            if (metadata.getDescription() == null || metadata.getDescription().isEmpty()) {
                description.setText("<No description specified>");
                description.setForeground(description.getForeground().darker());
            }
            // Builds the component
            JPanel component = new JPanel();
            component.setLayout(new BorderLayout());
            component.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
            if (isSelected) {
                component.setBackground(new Color(0xEDEDED));
            } else {
                if (index % 2 == 0) {
                    component.setBackground(new Color(0x555555));
                }
            }
            component.add(label);
            component.add(description, BorderLayout.SOUTH);
            component.setOpaque(true);
            component.setForeground(description.getForeground());
            return component;
        }

    }

}
