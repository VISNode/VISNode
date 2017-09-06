package visnode.application;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import visnode.application.parser.NodeNetworkParser;
import visnode.gui.FileFilterFactory;
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
        super("Save", IconFactory.get().create("fa:floppy-o"));
        this.parser = new NodeNetworkParser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileFilterFactory.projectFileFilter().apply(chooser);
        chooser.setDialogTitle("Save");
        chooser.setApproveButtonText("Save");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().endsWith(".vnp")) {
                file = new File(file.getAbsolutePath() + ".vnp");
            }
            try (PrintWriter writer = new PrintWriter(file, "UTF-8");) {
                writer.print(parser.toJson(VISNode.get().getModel().getNetwork()));
            } catch (IOException ex) {
                ExceptionHandler.get().handle(ex);
            }
        }
    }

}
