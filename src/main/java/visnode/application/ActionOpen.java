package visnode.application;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.AbstractAction;
import visnode.application.parser.NodeNetworkParser;
import visnode.commons.swing.FileChooserFactory;
import visnode.gui.IconFactory;

/**
 * Action for opening a new project
 */
public class ActionOpen extends AbstractAction {

    /** Node network parser */
    private final NodeNetworkParser parser;

    /**
     * Creates a new action
     */
    public ActionOpen() {
        super("Open", IconFactory.get().create("fa:folder-open"));
        this.parser = new NodeNetworkParser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserFactory.openProject().accept((File file) -> {
            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                try (BufferedReader br = new BufferedReader(isr)) {
                    StringBuilder sb = new StringBuilder();
                    String s = br.readLine();
                    while (s != null) {
                        sb.append(s);
                        s = br.readLine();
                    }
                    VISNode.get().getModel().setNetwork(parser.fromJson(sb.toString()));
                }
            } catch (IOException ex) {
                throw new InvalidOpenFileException(ex);
            }
        });
    }

}
