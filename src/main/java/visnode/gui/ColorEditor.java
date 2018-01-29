package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import visnode.application.Messages;
import com.github.rxsling.Buttons;

/**
 * Color editor
 */
public class ColorEditor extends JComponent implements ParameterComponent<Color> {

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
    }

    private void initGui() {
        setLayout(new BorderLayout());
        add(Buttons.create().text(Messages.get().message("color")).focusable(false).onClick((ev) -> {
            Color ret = JColorChooser.showDialog(null, "Choose a color", color);
            setValue(ret);
            valueListener.valueChanged(ret, ret);
        }));
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
