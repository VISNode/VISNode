package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import visnode.application.Messages;
import visnode.commons.MultiFile;
import visnode.commons.gui.Button;
import visnode.commons.gui.Buttons;
import visnode.commons.gui.Panel;
import visnode.commons.swing.FileChooserFactory;

/**
 * Files editor
 */
public class FilesEditor extends Panel implements ParameterComponent<MultiFile> {

    /** Slider image */
    private JSlider slider;
    /** Image selection */
    private MultiFile multiFile;

    /**
     * Creates a new file editor
     */
    public FilesEditor() {
        initGui();
    }
    
    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(Buttons.create().text(Messages.get().message("file.choose")).focusable(false).id("file"));
        add(buidSlider(), BorderLayout.SOUTH);
    }

    /**
     * Creates the image slider component
     * 
     * @return JComponent
     */
    private JComponent buidSlider() {
        slider = new JSlider(0, 0);
        slider.setPreferredSize(new Dimension(150, 25));
        slider.setEnabled(false);
        slider.setOpaque(false);
        slider.setFocusable(false);
        return slider;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(MultiFile value) {
        multiFile = value;
        if (multiFile != null && multiFile.getSize() > 1) {
            slider.setMaximum(multiFile.getSize() - 1);
            slider.setEnabled(true);
        }
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        findId("file").as(Button.class).addActionListener((e) -> {
            FileChooserFactory.openImages().accept((filesSelected) -> {
                multiFile = new MultiFile(filesSelected, 0);
                slider.setEnabled(false);
                if (multiFile.getSize() > 1) {
                    slider.setMaximum(multiFile.getSize() - 1);
                    slider.setEnabled(true);
                }
                valueListener.valueChanged(null, multiFile);
            });
        });
        slider.addChangeListener((ChangeEvent e) -> {
            if (multiFile != null) {
                multiFile = new MultiFile(multiFile.getFiles(), slider.getValue());
                valueListener.valueChanged(null, multiFile);
            }
        });
    }

}
