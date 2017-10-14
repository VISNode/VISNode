package visnode.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import visnode.commons.ScriptValue;
import visnode.commons.swing.WindowFactory;
import visnode.commons.swing.components.CodeEditor;

/**
 * Dynamic node value editor
 */
public class ScriptValueEditor extends JComponent implements ParameterComponent<ScriptValue> {

    /** Value */
    private ScriptValue value;
    /** Code button */
    private JButton codeButton;
    /** Value listener */
    private ValueListener valueListener;

    /**
     * Creates a dynamic node value editor
     */
    public ScriptValueEditor() {
        value = buildDefault();
        initGui();
        initEvents();
    }

    /**
     * Initialize the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(buildButton());
    }

    /**
     * Initialize the interface events
     */
    private void initEvents() {
        codeButton.addActionListener((e) -> {
            showDialog();
        });
    }

    /**
     * Builds the button code
     *
     * @return JComponent
     */
    private JComponent buildButton() {
        this.codeButton = new JButton("Code");
        codeButton.setIcon(IconFactory.get().create("fa:code"));
        return codeButton;
    }

    /**
     * Shows the pane in a dialog
     */
    public void showDialog() {
        WindowFactory.frame().title("Script editor").create((container) -> {
            container.add(new Editor());
        }).setVisible(true);
    }

    /**
     * Builds a default script
     *
     * @return ScriptValue
     */
    private ScriptValue buildDefault() {
        return new ScriptValue("");
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(ScriptValue value) {
        this.value = value;
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        this.valueListener = valueListener;
    }

    /**
     * Editor component
     */
    private class Editor extends JComponent {

        /** The code editor */
        private CodeEditor textArea;

        public Editor() {
            super();
            initGui();
            initEvents();
        }

        /**
         * Initialize the interface
         */
        private void initGui() {
            setLayout(new BorderLayout());
            add(buildToolbar(), BorderLayout.NORTH);
            add(buildCodePane());
        }

        /**
         * Builds the tool bar
         *
         * @return
         */
        private JComponent buildToolbar() {
            JToolBar toolbar = new JToolBar();
            toolbar.add(new ActionExecute());
            return toolbar;
        }

        /**
         * Initialize the interface events
         */
        private void initEvents() {
            this.textArea.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_F6) {
                        executeScript();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
        }

        /**
         * Execute the script
         */
        private void executeScript() {
            valueListener.valueChanged(0, new ScriptValue(textArea.getText()));
        }

        /**
         * Returns the initial code text
         *
         * @return String
         */
        private String getInitText() {
            if (value != null && value.hasValue()) {
                return value.getValue();
            }
            return "function process() {\n\n}";
        }

        /**
         * Builds the code pane
         *
         * @return JComponent
         */
        private JComponent buildCodePane() {
            this.textArea = new CodeEditor(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
            this.textArea.setText(getInitText());
            return this.textArea;
        }

        /**
         * Action for execute the script
         */
        private class ActionExecute extends AbstractAction {

            public ActionExecute() {
                super("Execute (F6)", IconFactory.get().create("fa:play"));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                executeScript();
            }

        }

    }

}
