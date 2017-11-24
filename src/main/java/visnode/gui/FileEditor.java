package visnode.gui;

import java.io.File;
import javax.swing.JComponent;
import visnode.application.Messages;
import visnode.commons.gui.Button;
import visnode.commons.swing.FileChooserFactory;

/**
 * File editor
 */
public class FileEditor extends Button implements ParameterComponent<File> {

    /** Value */
    private File value;
    
    /**
     * Creates a new file editor
     */
    public FileEditor() {
        super();
        value = null;
        text(Messages.get().message("file.choose")).focusable(false);
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
        onClick((e) -> {
            FileChooserFactory.openImage().accept((File file) -> {
                valueListener.valueChanged(null, (File) file);
            });
        });
    }
    
}
