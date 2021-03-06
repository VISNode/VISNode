package visnode.gui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.paim.commons.ImageExporter;
import org.paim.commons.ImageFactory;
import visnode.application.ExceptionHandler;
import visnode.application.ProcessMetadata;
import visnode.application.VISNode;
import visnode.commons.MultiFileInput;
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
    private JButton openProject = new JButton();

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
        initEvents();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(buildTabs());
    }

    /**
     * Initializes the events
     */
    private void initEvents() {
        openProject.addActionListener((evt) -> {
            new Http().get(metadata.getProjectUrl()).thenAccept((result) -> {
                VISNode.get().getController().open(result.asString());
                try {
                    BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/visnode/pdi/process/image/Lenna.png"));
                    File file = new File(System.getProperty("user.home") + "/.visnode/" + System.getProperty("user.name") + "Lenna.png");
                    ImageExporter.exportBufferedImage(ImageFactory.buildRGBImage(image), file);
                    VISNode.get().getModel().getNetwork().
                            setInput(new MultiFileInput(new File[]{file}));
                } catch (Exception e) {
                    ExceptionHandler.get().handle(e);
                }
                SwingUtilities.getWindowAncestor(ProcessInformationPane.this).dispose();
            });
        });
    }

    /**
     * Build the tabs
     *
     * @return JComponent
     */
    private JComponent buildTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Information", buildInformationPane());
        if (metadata.getScriptUrl() != null) {
            tabs.add("Script", buildScriptPane());
        }
        if (metadata.getCodeUrl() != null) {
            tabs.add("Code", buildCodePane());
        }
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
        CodeEditor textArea = new CodeEditor(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        new Http().get(metadata.getScriptUrl()).thenAccept((result) -> {
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
