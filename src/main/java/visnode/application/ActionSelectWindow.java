/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.application;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.AbstractAction;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;

/**
 * Action for changing the window level and width for DICOM files
 */
public class ActionSelectWindow extends AbstractAction {

    private JFormattedTextField fieldWindowLevel;
    private JFormattedTextField fieldWindowWidth;
    
    public ActionSelectWindow() {
        super(Messages.get().message("selectDicomWindow"), IconFactory.get().create("fa:sliders"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        WindowFactory.modal().interceptClose(() -> true).onClose((evt) -> updateRenderingOptions()).create((panel) -> {
            fieldWindowLevel = new JFormattedTextField(new DecimalFormat("#0"));
            fieldWindowLevel.setValue(VISNode.get().getModel().getUserPreferences().getRenderingOptions().getDicomWindowLevel());
            fieldWindowWidth = new JFormattedTextField(new DecimalFormat("#0"));
            fieldWindowWidth.setValue(VISNode.get().getModel().getUserPreferences().getRenderingOptions().getDicomWindowWidth());
            panel.setLayout(new GridLayout(0, 2, 5, 0));
            panel.add(new JLabel("Window level", JLabel.RIGHT));
            panel.add(fieldWindowLevel);
            panel.add(new JLabel("Window width", JLabel.RIGHT));
            panel.add(fieldWindowWidth);
        }).setVisible(true);
    }
    
    private void updateRenderingOptions() {
        VISNode.get().getModel().getUserPreferences().getRenderingOptions().setDicomWindowLevel(((Number) fieldWindowLevel.getValue()).intValue());
        VISNode.get().getModel().getUserPreferences().getRenderingOptions().setDicomWindowWidth(((Number) fieldWindowWidth.getValue()).intValue());
        VISNode.get().getController().repaintImagePreviews();
    }
    
}
