package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import visnode.application.VISNode;
import visnode.commons.MultiFileInput;
import visnode.commons.WebcamInput;
import com.github.rxsling.Buttons;
import com.github.rxsling.Component;
import com.github.rxsling.Inputs;
import com.github.rxsling.Panel;
import com.github.rxsling.Panels;
import com.github.rxsling.TextField;
import visnode.commons.swing.FileChooserFactory;
import visnode.pdi.process.ImageInput;

/**
 * Files editor
 */
public class InputEditor extends Panel implements ParameterComponent<ImageInput> {

    /** Value listeners */
    private final List<ValueListener> listeners;
    /** Slider image */
    private JSlider slider;
    /** Image selection */
    private ImageInput input;

    /**
     * Creates a new file editor
     */
    public InputEditor() {
        super();
        this.listeners = new ArrayList<>();
        initGui();
    }
    
    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        Panel panelTypes = Panels.buttonGroup();
        panelTypes.put(Buttons.toggle()
                .icon(IconFactory.get().create("fa:picture-o"))
                .hint("Select an image from the computer")
                .focusable(false)
                .onClick((e) -> setValue(new MultiFileInput())));
        panelTypes.put(Buttons.toggle()
                .icon(IconFactory.get().create("fa:video-camera"))
                .hint("Show webcam video")
                .focusable(false)
                .onClick((e) -> setValue(new WebcamInput())));
        add(panelTypes, BorderLayout.NORTH);
        buildSpecificFields();
    }
    
    /**
     * Builds the specific fields for the selected input method
     */
    private synchronized void buildSpecificFields() {
        SwingUtilities.invokeLater(() -> {
            if (findId("editor") != null) {
                remove((java.awt.Component) findId("editor"));
            }
            Component editor = null;
            if (input == null) {
                editor = Panels.create();
            } else {
                if (input instanceof MultiFileInput) {
                    editor = buildMultiFile();
                }
                if (input instanceof WebcamInput) {
                    editor = Panels.create();
                }
            }
            put(editor.id("editor"));
            revalidate();
        });
    }
    
    /**
     * Creates the editor for the MultiFile input
     * 
     * @return Component
     */
    private Component buildMultiFile() {
        Panel panel = new Panel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        panel.setLayout(new BorderLayout());
        Panel psup = new Panel();
        psup.setLayout(new BoxLayout(psup, BoxLayout.X_AXIS));
        psup.put(Inputs.text().enabled(false).preferredSize(100, 22).id("multiFileName"));
        psup.put(Buttons.create().focusable(false).icon(IconFactory.get().create("fa:folder-open")).onClick((evt) -> {
            FileChooserFactory.openImages().accept((filesSelected) -> {
                input = new MultiFileInput(filesSelected, 0);
                fireValueChanged();
            });
        }));
        psup.put(Buttons.create().focusable(false).icon(IconFactory.get().create("fa:chevron-down")).onClick((evt) -> {
            JPopupMenu menu = new JPopupMenu();
            for (File file : VISNode.get().getModel().getUserPreferences().getRecentInput()) {
                JMenuItem item = new JMenuItem(file.getAbsolutePath());
                item.addActionListener((e) -> {
                    input = new MultiFileInput(file);
                    fireValueChanged();
                });
                menu.add(item);
            }
            menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
        }));
        panel.add(psup);
        panel.add(buidSlider(), BorderLayout.SOUTH);
        SwingUtilities.invokeLater(() -> updateMultifileFields((MultiFileInput) input));
        return panel;
    }
    
    /**
     * Creates the image slider component
     * 
     * @return JComponent
     */
    private JComponent buidSlider() {
        slider = new JSlider(0, 0);
        slider.setPreferredSize(new Dimension(120, 25));
        slider.setEnabled(false);
        slider.setOpaque(false);
        slider.setFocusable(false);
        slider.addChangeListener((ChangeEvent e) -> {
            if (input != null) {
                if (input instanceof MultiFileInput) {
                    MultiFileInput multi = (MultiFileInput) input;
                    input = new MultiFileInput(multi.getFiles(), slider.getValue());
                    fireValueChanged();
                }
            }
        });
        return slider;
    }
    
    /**
     * Update the value of the fields
     */
    private void updateFields() {
        if (input instanceof MultiFileInput) {
            updateMultifileFields((MultiFileInput) input);
        }
    }
    
    /**
     * Update the value for the MultiFile fields
     * 
     * @param multiFile 
     */
    private void updateMultifileFields(MultiFileInput multiFile) {
        if (slider == null || findId("multiFileName") == null) {
            return;
        }
        StringBuilder text = new StringBuilder();
        slider.setEnabled(false);
        if (multiFile.getSize() > 1) {
            text.append('[').append(multiFile.getIndex() + 1).append('/').append(multiFile.getSize()).append("] ");
            slider.setMaximum(multiFile.getSize() - 1);
            slider.setValue(multiFile.getIndex());
            slider.setEnabled(true);
            text.append(multiFile.getFile().getName());
        }
        findId("multiFileName").as(TextField.class).value(text.toString());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(ImageInput value) {
        boolean changeOfClass = input == null || !value.getClass().equals(input.getClass());
        input = value;
        if (input != null) {
            if (input instanceof MultiFileInput) {
                MultiFileInput multiFile = (MultiFileInput) input;
                if (multiFile.getSize() > 1 && slider != null) {
                    slider.setMaximum(multiFile.getSize() - 1);
                    slider.setEnabled(true);
                }
            }
        }
        if (changeOfClass) {
            buildSpecificFields();
        }
        fireValueChanged();
    }
    
    /**
     * Fires a value change event
     */
    private void fireValueChanged() {
        updateFields();
        if (input instanceof MultiFileInput) {
            MultiFileInput files = (MultiFileInput) input;
            for (File file : files.getFiles()) {
                VISNode.get().getModel().getUserPreferences().addRecentInput(file);
            }
        }
        for (ValueListener listener : listeners) {
            listener.valueChanged(null, input);
        }
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        listeners.add(valueListener);
    }

}
