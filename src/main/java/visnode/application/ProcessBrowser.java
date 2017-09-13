package visnode.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import visnode.gui.IconFactory;
import visnode.gui.ProcessInformationPane;
import visnode.pdi.Process;
import visnode.pdi.process.AverageBlurProcess;
import visnode.pdi.process.BrightnessProcess;
import visnode.pdi.process.ClosingProcess;
import visnode.pdi.process.ContrastProcess;
import visnode.pdi.process.DilationProcess;
import visnode.pdi.process.DynamicPixelProcess;
import visnode.pdi.process.ErosionProcess;
import visnode.pdi.process.GaussianBlurProcess;
import visnode.pdi.process.GrayscaleProcess;
import visnode.pdi.process.HoltProcess;
import visnode.pdi.process.HorizontalMirroringProcess;
import visnode.pdi.process.InformationProcess;
import visnode.pdi.process.InvertColorProcess;
import visnode.pdi.process.ResizeProcess;
import visnode.pdi.process.KirshProcess;
import visnode.pdi.process.MedianBlurProcess;
import visnode.pdi.process.ModeBlurProcess;
import visnode.pdi.process.OpeningProcess;
import visnode.pdi.process.RobertsProcess;
import visnode.pdi.process.RobinsonProcess;
import visnode.pdi.process.RotateProcess;
import visnode.pdi.process.SobelProcess;
import visnode.pdi.process.StentifordProcess;
import visnode.pdi.process.ThresholdProcess;
import visnode.pdi.process.TranslateProcess;
import visnode.pdi.process.VerticalMirroringProcess;
import visnode.pdi.process.WeightedGrayscaleProcess;
import visnode.pdi.process.ZhangSuenProcess;

/**
 * Process browser
 */
public class ProcessBrowser extends JComponent {

    /** Process list */
    private JList<Class<Process>> list;
    
    /**
     * Creates the process browser
     */
    public ProcessBrowser() {
        super();
        initGui();
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
     * Builds the filter panel
     * 
     * @return JComponent
     */
    private JComponent buildFilterPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(new JLabel(IconFactory.get().create("fa:search")), BorderLayout.WEST);
        panel.add(buildFilterField());
        panel.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 3));
        return panel;
    }
    
    /**
     * Creates the field for name filtering
     * 
     * @return JComponent
     */
    private JComponent buildFilterField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(100, 25));
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter();
            }
            /**
             * Updates the filter
             */
            private void updateFilter() {
                updateList(field.getText());
            }
        });
        return field;
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
        updateList();
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() % 2 == 0) {
                    ProcessInformationPane.showDialog(list.getSelectedValue());
                }
            }
        });
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(list);
        return scroll;
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
        if (filter != null) {
            filter = filter.toLowerCase();
        }
        DefaultListModel<Class<Process>> model = new DefaultListModel();
        for (Class<Process> process : getProcesses()) {
            if (filter != null) {
                ProcessMetadata metadata = ProcessMetadata.fromClass(process);
                if (!metadata.getName().toLowerCase().contains(filter) && !metadata.getDescription().toLowerCase().contains(filter)) {
                    continue;
                }
            }
            model.addElement(process);
        }
        list.setModel(model);
    }
    
    /**
     * Returns the processes
     * 
     * @return Process[]
     */
    private Class<Process>[] getProcesses() {
        Class[] process = new Class[] {
            DynamicPixelProcess.class,
            BrightnessProcess.class,
            ContrastProcess.class,
            GrayscaleProcess.class,
            WeightedGrayscaleProcess.class,
            RobertsProcess.class,
            SobelProcess.class,
            GaussianBlurProcess.class,
            InformationProcess.class,
            InvertColorProcess.class,
            RobinsonProcess.class,
            KirshProcess.class,
            DilationProcess.class,
            ErosionProcess.class,
            OpeningProcess.class,
            ClosingProcess.class,
            ResizeProcess.class,
            RotateProcess.class,
            TranslateProcess.class,
            HorizontalMirroringProcess.class,
            VerticalMirroringProcess.class,
            ZhangSuenProcess.class,
            HoltProcess.class,
            StentifordProcess.class,
            AverageBlurProcess.class,
            MedianBlurProcess.class,
            ModeBlurProcess.class,
            ThresholdProcess.class
        };
        Arrays.sort(process, (it1, it2) -> {
            return it1.getName().compareTo(it2.getName());
        });
        return process;
    }
    
    private class CellRenderer implements ListCellRenderer<Class<Process>> {

        /** Renderer to base background and foreground color */
        private final ListCellRenderer defaultRenderer;

        /**
         * Creates 
         * @param defaultRenderer 
         */
        public CellRenderer(ListCellRenderer defaultRenderer) {
            this.defaultRenderer = defaultRenderer;
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Class<Process>> list, Class<Process> value, int index, boolean isSelected, boolean cellHasFocus) {
            ProcessMetadata metadata = ProcessMetadata.fromClass(value);
            // Title label
            JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
            label.setText(metadata.getName());
            label.setFont(new Font("Arial", Font.BOLD, 12));
            // Description label
            JLabel description = new JLabel(metadata.getDescription());
            description.setForeground(description.getForeground());
            description.setBorder(BorderFactory.createEmptyBorder(1, 10, 3, 3));
            description.setFont(new Font("Arial", Font.PLAIN, 10));
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
