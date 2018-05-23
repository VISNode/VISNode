package visnode.commons.swing.components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import javax.swing.JComponent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import visnode.application.ExceptionHandler;

/**
 * Code editor
 */
public class CodeEditor extends JComponent {

    /** Syntax text area */
    private RSyntaxTextArea textArea;
    /** Syntax style */
    private String syntaxStyle;

    /**
     * Creates the code editor
     */
    public CodeEditor() {
        this(SyntaxConstants.SYNTAX_STYLE_JAVA);
    }

    /**
     * Creates the code editor
     *
     * @param syntaxStyle
     */
    public CodeEditor(String syntaxStyle) {
        super();
        this.syntaxStyle = syntaxStyle;
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
        textArea.setSyntaxEditingStyle(syntaxStyle);
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
     * Set the font for all token types
     *
     * @param size The font size
     */
    public void increaseFont(int size) {
        Font font = textArea.getFont();
        setFont(new Font(font.getName(), font.getStyle(), font.getSize() + size));
    }

    /**
     * Set the font for all token types
     *
     * @param font The font to use
     */
    @Override
    public void setFont(Font font) {
        if (font == null) {
            return;
        }
        SyntaxScheme ss = textArea.getSyntaxScheme();
        ss = (SyntaxScheme) ss.clone();
        for (int i = 0; i < ss.getStyleCount(); i++) {
            if (ss.getStyle(i) != null) {
                ss.getStyle(i).font = font;
            }
        }
        textArea.setSyntaxScheme(ss);
        textArea.setFont(font);
    }

    @Override
    public Font getFont() {
        return textArea.getFont();
    }
    
    /**
     * Sets the code text
     *
     * @param text
     */
    public void setText(String text) {
        textArea.setText(text);
    }

    /**
     * Gets the code text
     *
     * @return String
     */
    public String getText() {
        return textArea.getText();
    }

    @Override
    public void setEnabled(boolean enabled) {
        textArea.setEnabled(enabled);
    }

    @Override
    public synchronized void addKeyListener(KeyListener keyListener) {
        textArea.addKeyListener(keyListener);
    }

    @Override
    public synchronized void addMouseWheelListener(MouseWheelListener keyListener) {
        textArea.addMouseWheelListener(keyListener);
    }

    /**
     * Sets if the field is editable
     *
     * @param editable
     */
    public void setEditable(boolean editable) {
        textArea.setEditable(editable);
    }

}
