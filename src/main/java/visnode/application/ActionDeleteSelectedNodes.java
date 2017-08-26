
package visnode.application;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import visnode.executor.EditNodeDecorator;
import visnode.gui.IconFactory;
import visnode.gui.Selection;

/**
 * Action - Delete selected nodes
 */
public class ActionDeleteSelectedNodes extends AbstractAction {

    /**
     * Creates a new action
     */
    public ActionDeleteSelectedNodes() {
        super("Delete seletected nodes", IconFactory.get().create("fa:trash"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Selection<NodeView> selection = VISNode.get().getNetworkEditor().getSelection();
        List<EditNodeDecorator> nodes = selection.stream().map((view) -> view.getModel()).collect(Collectors.toList());
        VISNode.get().getModel().getNetwork().remove(nodes);
    }

}
