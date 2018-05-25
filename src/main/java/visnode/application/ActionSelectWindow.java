package visnode.application;

import com.github.rxsling.Labels;
import com.github.rxsling.enums.HAlign;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import org.paim.commons.DicomWindow;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;

/**
 * Action for changing the window level and width for DICOM files
 */
public class ActionSelectWindow extends AbstractAction {

    private JFormattedTextField fieldWindowLevel;
    private JFormattedTextField fieldWindowWidth;

    public ActionSelectWindow() {
        super();
        putValue(SMALL_ICON, IconFactory.get().create("fa:sliders"));
        Messages.get().message("selectDicomWindow").subscribe((msg) -> {
            putValue(NAME, msg);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Messages.get().message("selectDicomWindow").subscribe((msg) -> {
            WindowFactory.modal().title(msg).interceptClose(() -> true).onClose((evt) -> updateRenderingOptions()).create((panel) -> {
                fieldWindowLevel = new JFormattedTextField(new DecimalFormat("#0"));
                fieldWindowLevel.setValue(VISNode.get().getModel().getUserPreferences().getRenderingOptions().getDicomWindowLevel());
                fieldWindowWidth = new JFormattedTextField(new DecimalFormat("#0"));
                fieldWindowWidth.setValue(VISNode.get().getModel().getUserPreferences().getRenderingOptions().getDicomWindowWidth());
                panel.setLayout(new GridLayout(0, 2, 5, 0));
                panel.add(Labels.create("Window level", HAlign.RIGHT));
                panel.add(fieldWindowLevel);
                panel.add(Labels.create("Window width", HAlign.RIGHT));
                panel.add(fieldWindowWidth);
                panel.add(Labels.create("Default", HAlign.RIGHT));
                panel.add(buildFilter());
            }).setVisible(true);
        });
    }

    /**
     * Creates a default filter
     *
     * @return JComponent
     */
    private JComponent buildFilter() {
        JComboBox<Window> filter = new JComboBox<>();
        filter.addItem(new Window(0, 0).legend("Custom"));
        for (DicomWindow window : DicomWindow.values()) {
            Window obj = new Window(window.getWindowLevel(), window.getWindowWidth());
            Messages.get().message(window.toString().toLowerCase()).subscribe((msg) -> {
                obj.legend(msg);
            });
            filter.addItem(obj);
            if (obj.windowLevel == ((Number) fieldWindowLevel.getValue()).intValue()
                    && obj.windowWidth == ((Number) fieldWindowWidth.getValue()).intValue()) {
                filter.setSelectedItem(obj);
            }
        }
        filter.addActionListener((ac) -> {
            Window window = (Window) filter.getModel().getSelectedItem();
            fieldWindowLevel.setValue(window.windowLevel);
            fieldWindowWidth.setValue(window.windowWidth);
        });
        return filter;
    }

    private void updateRenderingOptions() {
        VISNode.get().getModel().getUserPreferences().getRenderingOptions().setDicomWindowLevel(((Number) fieldWindowLevel.getValue()).intValue());
        VISNode.get().getModel().getUserPreferences().getRenderingOptions().setDicomWindowWidth(((Number) fieldWindowWidth.getValue()).intValue());
        VISNode.get().getController().repaintImagePreviews();
    }

    private class Window {

        /** Legend */
        private String legend;
        /** Window level */
        private final int windowLevel;
        /** Window width */
        private final int windowWidth;

        public Window(int windowLevel, int windowWidth) {
            this.windowLevel = windowLevel;
            this.windowWidth = windowWidth;
        }

        public Window legend(String legend) {
            this.legend = legend;
            return this;
        }

        @Override
        public String toString() {
            return legend;
        }

    }

}
