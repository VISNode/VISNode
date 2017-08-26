package visnode.application;

import java.awt.BorderLayout;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JList;
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
    
}
