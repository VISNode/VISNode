package visnode.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import org.torax.commons.Image;
import visnode.commons.TypeConverter;
import visnode.gui.ImageNodeComponent;
import visnode.pdi.Process;
import visnode.pdi.process.BrightnessProcess;
import visnode.pdi.process.ContrastProcess;
import visnode.pdi.process.DynamicPixelProcess;
import visnode.pdi.process.GaussianBlurProcess;
import visnode.pdi.process.GrayscaleProcess;
import visnode.pdi.process.InformationProcess;
import visnode.pdi.process.InvertColorProcess;
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
            GaussianBlurProcess.class,
            InformationProcess.class,
            InvertColorProcess.class,
            ThresholdProcess.class
        };
    }
    
    /**
     * Returns the prettyfied name for the process class
     * 
     * @param processType
     * @return String
     */
    private String getNameFor(Class<Process> processType) {
        String name = processType.getSimpleName();
        name = name.replaceFirst("Process$", "");
        name = name.replaceAll("([a-z])([A-Z])", "$1 $2");
        return name;
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
            JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
            JPanel component = new JPanel(new BorderLayout());
            component.add(label);
            label.setText(getNameFor(value));
            component.setOpaque(true);
            component.setBackground(label.getBackground());
            component.setForeground(label.getForeground());
            return label;
        }
        
    }
    
}
