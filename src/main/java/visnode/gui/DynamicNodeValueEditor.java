package visnode.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import visnode.commons.DynamicNodeValue;

/**
 * Dynamic node value editor
 */
public class DynamicNodeValueEditor extends JTextArea implements ParameterComponent<DynamicNodeValue> {

    /** Value */
    private DynamicNodeValue value;
    
    /**
     * Creates a dynamic node value editor
     */
    public DynamicNodeValueEditor() {
        super(5, 30);
        value = buildDefault();
    }
    
    /**
     * Builds a default script
     * 
     * @return DynamicNodeValue
     */
    private DynamicNodeValue buildDefault() {
        return new DynamicNodeValue("");
    }
    
    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(DynamicNodeValue value) {
        if (value != null) {
            this.value = value;
        } else {
            this.value = buildDefault();
        }
        super.setText(this.value.getValue());
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F6) {
                    valueListener.valueChanged(0, new DynamicNodeValue(getText()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
}
