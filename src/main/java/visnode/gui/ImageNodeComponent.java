package visnode.gui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.application.OutputImageFactory;

/**
 * Image component
 */
public class ImageNodeComponent extends JComponent implements ParameterComponent<Image> {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 150;
    /** Image */
    private Image value;
    /** Image */
    private ImageIcon icon;

    /**
     * Creates a new image component
     */
    public ImageNodeComponent() {
        this.value = ImageFactory.buildEmptyImage();
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(buildImage());
        updateImage();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() % 2 == 0) {
                    ImageViewerPanel.showDialog(value);
                }
            }
        });
    }

    /**
     * Builds the image
     *
     * @return JComponent
     */
    private JComponent buildImage() {
        this.icon = new ImageIcon();
        return new JLabel(icon);
    }

    /**
     * Updates the image
     */
    private void updateImage() {
        BufferedImage image = OutputImageFactory.getBuffered(value);
        int size = Math.max(image.getWidth(), image.getHeight());
        int newWidth = THUMBNAIL_SIZE * image.getWidth() / size;
        int newHeight = THUMBNAIL_SIZE * image.getHeight() / size;
        int x = (THUMBNAIL_SIZE - newWidth) / 2;
        int y = (THUMBNAIL_SIZE - newHeight) / 2;
        BufferedImage newImage = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        java.awt.Image scaledImage = image.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        newImage.getGraphics().drawImage(scaledImage, x, y, null);
        icon.setImage(newImage);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(icon.getImage(), 0, 0, this);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Image value) {
        this.value = value;
        updateImage();
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        // This component is read only
    }

}
