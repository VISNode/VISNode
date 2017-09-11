package visnode.commons.swing.components;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JComponent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import visnode.application.ExceptionHandler;

/**
 * Code editor
 */
public class CodeEditor extends JComponent {
    
    /** Syntax text area */
    private RSyntaxTextArea textArea;

    /**
     * Creates the code editor
     */
    public CodeEditor() {
        super();
        initGui();
    }
    
    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(buildCodePane());
    }
    
    /**
     * Builds the code pane
     *
     * @return JComponent
     */
    private JComponent buildCodePane() {
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream("CodeEditorTheme.xml"));
            theme.apply(textArea);
        } catch (IOException e) {
            ExceptionHandler.get().handle(e);
        }
        return new RTextScrollPane(textArea);
    }
    
    /**
     * Sets the code text
     * 
     * @param text 
     */
    public void setText(String text) {
        textArea.setText(text);
    }

    @Override
    public void setEnabled(boolean enabled) {
        textArea.setEnabled(enabled);
    }
    
    /**
     * Define se o campo é editável
     * 
     * @param editable 
     */
    public void setEditable(boolean editable) {
        textArea.setEditable(editable);
    }
    
}
