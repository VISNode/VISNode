package visnode.gui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import visnode.application.ProcessMetadata;
import visnode.application.VISNode;
import visnode.commons.http.Http;
import visnode.commons.swing.WindowFactory;
import visnode.commons.swing.components.CodeEditor;
import visnode.commons.swing.components.MarkdownViewer;
import visnode.pdi.Process;

/**
 * Panel with information about the process
 */
public class ProcessInformationPane extends JPanel {

    /** Process type */
    private final Class<? extends Process> type;
    /** Process meta-data */
    private final ProcessMetadata metadata;
    /** Action open project */
    private JButton openProject;

    /**
     * Creates a new Process Info panel
     *
     * @param type
     */
    public ProcessInformationPane(Class<? extends Process> type) {
        super();
        this.type = type;
        this.metadata = ProcessMetadata.fromClass(type, VISNode.get().getModel().getUserPreferences().getLocale());
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(buildTabs());
    }

    /**
     * Build the tabs
     *
     * @return JComponent
     */
    private JComponent buildTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Information", buildInformationPane());
        tabs.add("Script", buildScriptPane());
        tabs.add("Code", buildCodePane());
        return tabs;
    }

    /**
     * Builds the information pane
     *
     * @return JComponent
     */
    private JComponent buildInformationPane() {
        MarkdownViewer viewer = new MarkdownViewer();
        add(viewer);
        if (metadata.getHelpUrl() != null) {
            viewer.loadUrl(metadata.getHelpUrl());
        }
        return viewer;
    }

    /**
     * Builds the code pane
     *
     * @return JComponent
     */
    private JComponent buildCodePane() {
        CodeEditor textArea = new CodeEditor();
        new Http().get(metadata.getCodeUrl()).thenAccept((result) -> {
            textArea.setText(result.asString());
        });
        textArea.setEditable(false);
        return textArea;
    }

    /**
     * Builds the script pane
     *
     * @return JComponent
     */
    private JComponent buildScriptPane() {
        CodeEditor textArea = new CodeEditor();
        new Http().get(metadata.getCodeUrl()).thenAccept((result) -> {
            textArea.setText(result.asString());
        });
        textArea.setEditable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(buildScriptPaneButtons(), BorderLayout.NORTH);
        panel.add(textArea);
        return panel;
    }

    /**
     * Builds the script pane
     *
     * @return JComponent
     */
    private JComponent buildScriptPaneButtons() {
        openProject = new JButton("Open", IconFactory.get().create("fa:folder-open"));
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        buttons.add(openProject, BorderLayout.EAST);
        return buttons;
    }

    /**
     * Shows the pane in a dialog
     *
     * @param process
     */
    public static void showDialog(Class<? extends Process> process) {
        WindowFactory.frame().create((container) -> {
            container.add(new ProcessInformationPane(process));
        }).setVisible(true);
    }

}
