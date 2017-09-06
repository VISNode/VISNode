package visnode.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import visnode.pdi.Process;
import visnode.pdi.process.BrightnessProcess;
import visnode.pdi.process.ContrastProcess;
import visnode.pdi.process.DynamicPixelProcess;
import visnode.pdi.process.GaussianBlurProcess;
import visnode.pdi.process.GrayscaleProcess;
import visnode.pdi.process.InformationProcess;
import visnode.pdi.process.InvertColorProcess;
import visnode.pdi.process.RobertsProcess;
import visnode.pdi.process.SobelProcess;
import visnode.pdi.process.ThresholdProcess;

/**
 * Process browser
 */
public class ProcessBrowser extends JComponent {

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
        add(buildList());
    }

    /**
     * Creates the process list
     * 
     * @return JComponent
     */
    private JComponent buildList() {
        JList<Class<Process>> list = new JList<>(getProcesses());
        list.setCellRenderer(new CellRenderer(list.getCellRenderer()));
        list.setTransferHandler(new ProcessTransferHandler());
        list.setDragEnabled(true);
        list.setDropMode(DropMode.ON_OR_INSERT);
        return list;
    }
    
    /**
     * Returns the processes
     * 
     * @return Process[]
     */
    private Class<Process>[] getProcesses() {
        return new Class[] {
            DynamicPixelProcess.class,
            BrightnessProcess.class,
            ContrastProcess.class,
            GrayscaleProcess.class,
            RobertsProcess.class,
            SobelProcess.class,
            GaussianBlurProcess.class,
            InformationProcess.class,
            InvertColorProcess.class,
            ThresholdProcess.class
        };
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
