package visnode.gui;

import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import visnode.commons.swing.FileChooserFactory;

/**
 * File editor
 */
public class FileEditor extends JButton implements ParameterComponent<File> {

    /** Value */
    private File value;
    
    /**
     * Creates a new file editor
     */
    public FileEditor() {
        super("Choose file");
        value = null;
        setFocusable(false);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(File value) {
        if (value != null) {
            this.value = value;
        } else {
            this.value = null;
        }
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        addActionListener((e) -> {
            FileChooserFactory.openImage().accept((File file) -> {
                valueListener.valueChanged(null, (File) file);
            });
        });
    }
    
}
