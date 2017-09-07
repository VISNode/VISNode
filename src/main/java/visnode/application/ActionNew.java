
package visnode.application;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import visnode.gui.IconFactory;

/**
 *
 * @author Nícolas Pohren
 */
public class ActionNew extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionNew() {
        super("New", IconFactory.get().create("fa:file"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        VISNode.get().getModel().setNetwork(NodeNetworkFactory.createEmtpy());
    }

}
