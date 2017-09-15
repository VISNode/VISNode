package visnode.gui;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.paim.commons.Image;
import visnode.application.OutputImageFactory;
import visnode.commons.swing.WindowFactory;

/**
 * Image viewer dialog
 */
public class ImageViewerPanel extends JPanel {

    /** Image */
    private final Image image;

    /**
     * Creates a new image viewer dialog
     * 
     * @param image 
     */
    public ImageViewerPanel(Image image) {
        super();
        this.image = image;
        initGui();
    }
    
    /**
     * Shows the dialog
     * 
     * @param image 
     */
    public static void showDialog(Image image) {
        WindowFactory.frame().title("Image").create((container) -> {
            container.add(new ImageViewerPanel(image));
        }).setVisible(true);
    }
    
    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(new JLabel(new ImageIcon(OutputImageFactory.getBuffered(image))));
    }
    
}
