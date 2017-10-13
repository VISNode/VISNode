package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import visnode.gui.AboutVISNodePanel;
import visnode.gui.IconFactory;

/**
 * Action for opening information about VISNode 
 */
public class ActionAboutVisnode extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionAboutVisnode() {
        super(Messages.get().message("aboutVisnode"), IconFactory.get().create("fa:info"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        AboutVISNodePanel.showDialog();
    }

}
