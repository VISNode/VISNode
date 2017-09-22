package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Color editor
 */
public class ColorEditor extends JComponent implements ParameterComponent<Color> {

    /** Field */
    private JButton field;
    /** Color */
    private Color color;
    /** Value listener */
    private ValueListener valueListener;
    
    /**
     * Creates a new integer editor
     */
    public ColorEditor() {
        super();
        initGui();
        initEvents();
    }

    private void initGui() {
        setLayout(new BorderLayout());
        field = new JButton("Color");
        add(field);
    }
    
    private void initEvents() {
        field.addActionListener((ev) -> {
            Color ret = JColorChooser.showDialog(null, "Choose a color", color);
            setValue(ret);
            valueListener.valueChanged(ret, ret);
        });
    }
    
    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Color value) {
        if (value == null) {
            value = new Color(0);
        }
        color = value;
    }

    /**
     * Returns the value
     * 
     * @return Color
     */
    public Color getValue() {
        return color;
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        this.valueListener = valueListener;
    }
    

}
