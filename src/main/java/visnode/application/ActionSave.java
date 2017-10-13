package visnode.application;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.AbstractAction;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.KeyStroke;
import visnode.application.parser.NodeNetworkParser;
import visnode.commons.swing.FileChooserFactory;
import visnode.gui.IconFactory;

/**
 * Action for saving the current project
 */
public class ActionSave extends AbstractAction {

    /** Node network parser */
    private final NodeNetworkParser parser;

    /**
     * Creates a new action
     */
    public ActionSave() {
        super(Messages.get().message("save") + "...", IconFactory.get().create("fa:floppy-o"));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        this.parser = new NodeNetworkParser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserFactory.saveProject().accept((file) -> {
            try (PrintWriter writer = new PrintWriter(file, "UTF-8");) {
                writer.print(parser.toJson(VISNode.get().getModel().getNetwork()));
            } catch (IOException ex) {
                ExceptionHandler.get().handle(ex);
            }
        });
    }

}
