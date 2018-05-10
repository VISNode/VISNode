package visnode.application;

import com.github.rxsling.Labels;
import com.github.rxsling.enums.HAlign;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.AbstractAction;
import javax.swing.JFormattedTextField;
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
        WindowFactory.modal().interceptClose(() -> true).onClose((evt) -> updateRenderingOptions()).create((panel) -> {
            fieldWindowLevel = new JFormattedTextField(new DecimalFormat("#0"));
            fieldWindowLevel.setValue(VISNode.get().getModel().getUserPreferences().getRenderingOptions().getDicomWindowLevel());
            fieldWindowWidth = new JFormattedTextField(new DecimalFormat("#0"));
            fieldWindowWidth.setValue(VISNode.get().getModel().getUserPreferences().getRenderingOptions().getDicomWindowWidth());
            panel.setLayout(new GridLayout(0, 2, 5, 0));
            panel.add(Labels.create("Window level", HAlign.RIGHT));
            panel.add(fieldWindowLevel);
            panel.add(Labels.create("Window width", HAlign.RIGHT));
            panel.add(fieldWindowWidth);
        }).setVisible(true);
    }

    private void updateRenderingOptions() {
        VISNode.get().getModel().getUserPreferences().getRenderingOptions().setDicomWindowLevel(((Number) fieldWindowLevel.getValue()).intValue());
        VISNode.get().getModel().getUserPreferences().getRenderingOptions().setDicomWindowWidth(((Number) fieldWindowWidth.getValue()).intValue());
        VISNode.get().getController().repaintImagePreviews();
    }

}
