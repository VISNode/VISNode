/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import visnode.commons.Image;
import static visnode.gui.ImageNodeComponent.getBuffered;

/**
 * Image viewer dialog
 */
public class ImageViewerDialog extends JDialog {

    /** Image */
    private final Image image;

    /**
     * Creates a new image viewer dialog
     * 
     * @param image 
     */
    public ImageViewerDialog(Image image) {
        super((Window) null);
        this.image = image;
        initGui();
    }
    
    /**
     * Shows the dialog
     * 
     * @param image 
     */
    public static void show(Image image) {
        ImageViewerDialog dialog = new ImageViewerDialog(image);
        dialog.setVisible(true);
    }
    
    /**
     * Initializes the interface
     */
    private void initGui() {
        setTitle("Image viewer");
        setModal(true);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JLabel(new ImageIcon(getBuffered(image))));
        pack();
    }
    
}
