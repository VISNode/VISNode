package visnode.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import visnode.commons.ScriptValue;
import com.github.rxsling.Buttons;
import com.github.rxsling.Panel;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import visnode.commons.swing.WindowFactory;
import visnode.commons.swing.components.CodeEditor;

/**
 * Dynamic node value editor
 */
public class ScriptValueEditor extends Panel implements ParameterComponent<ScriptValue> {

    /** Value */
    private ScriptValue value;
    /** Value listener */
    private ValueListener valueListener;
    /** Font */
    private static Font font;

    /**
     * Creates a dynamic node value editor
     */
    public ScriptValueEditor() {
        value = buildDefault();
        initGui();
    }

    /**
     * Initialize the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(Buttons.create("Code").icon(IconFactory.get().create("fa:code")).onClick((e) -> {
            showDialog();
        }));
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
        /** Log */
        private JTextArea log;
        /** Panel log */
        private JComponent panelLog;
        
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
            add(buildLog(), BorderLayout.SOUTH);
        }

        /**
         * Builds the tool bar
         *
         * @return
         */
        private JComponent buildToolbar() {
            JToolBar toolbar = new JToolBar();
            toolbar.add(new ActionExecute());
            toolbar.add(new ActionLog());
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
            this.textArea.addMouseWheelListener((ev) -> {
                if (ev.isControlDown()) {
                    textArea.increaseFont(-ev.getWheelRotation());
                    font = textArea.getFont();
                }
            });
            ScriptValueEditorLog.get().observable().subscribe((logText) -> {
                if (logText == null || logText.isEmpty()) {
                    return;
                }
                if (!panelLog.isVisible()) {
                    SwingUtilities.invokeLater(() -> {
                        panelLog.setVisible(true);
                    });
                }
                this.log.append(logText);
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
            textArea = new CodeEditor(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
            textArea.setText(getInitText());
            if (font != null) {
                textArea.setFont(font);
            }
            return textArea;
        }

        /**
         * Build the log field
         *
         * @return JComponent
         */
        private JComponent buildLog() {
            log = new JTextArea();
            log.setEditable(false);
            log.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panelLog = new JPanel();
            panelLog.setLayout(new BorderLayout());
            panelLog.setPreferredSize(new Dimension(0, 150));
            panelLog.add(ScrollFactory.pane(log).create());
            panelLog.setVisible(false);           
            return panelLog;
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
       
        /**
         * Action for open the log
         */
        private class ActionLog extends AbstractAction {

            public ActionLog() {
                super("Output console", IconFactory.get().create("fa:window-restore"));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                panelLog.setVisible(!panelLog.isVisible());
            }

        }

    }

}
