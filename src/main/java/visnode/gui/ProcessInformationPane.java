package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import visnode.application.ExceptionHandler;
import visnode.application.ProcessMetadata;
import visnode.commons.http.Http;
import visnode.commons.swing.components.MarkdownViewer;
import visnode.pdi.Process;

/**
 * Panel with information about the process
 */
public class ProcessInformationPane extends JPanel {

    /** Process type */
    private final Class<? extends Process> type;
    /** Process metadata */
    private final ProcessMetadata metadata;

    /**
     * Creates a new Process Info panel
     * 
     * @param type 
     */
    public ProcessInformationPane(Class<? extends Process> type) {
        super();
        this.type = type;
        this.metadata = ProcessMetadata.fromClass(type);
        initGui();
    }
    
    /**
     * Initializes the interface
     */
    private void initGui() {
        MarkdownViewer viewer = new MarkdownViewer();
        add(viewer);
        if (metadata.getHelpUrl() != null) {
            viewer.loadUrl(metadata.getHelpUrl());
        }
    }
    
    /**
     * Shows the pane in a dialog
     * 
     * @param process 
     */
    public static void showDialog(Class<? extends Process> process) {
        ProcessInformationPane pane = new ProcessInformationPane(process);
        JDialog dialog = new JDialog();
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(pane);
        dialog.setModal(true);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
}
