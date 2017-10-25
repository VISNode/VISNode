package visnode.application;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;

/**
 * Action for opening a project directly
 */
public class ActionDirectOpen extends AbstractAction {

    /** File to open */
    private final File file;
    
    /**
     * Creates a new action
     * 
     * @param file
     */
    public ActionDirectOpen(File file) {
        super(file.getAbsolutePath());
        this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        VISNode.get().getController().open(file);
    }

}
