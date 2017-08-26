package visnode.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import visnode.commons.Image;
import visnode.commons.ImageFactory;

/**
 * Image component
 */
public class ImageNodeComponent extends JComponent implements ParameterComponent<Image> {

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
                    ImageViewerDialog.show(value);
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

    public static BufferedImage getBuffered(Image image) {
        BufferedImage buff = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int r, g, b;
                if (image.getChannelCount() == Image.CHANNELS_RGB) {
                    r = image.get(Image.CHANNEL_RED, x, y);
                    g = image.get(Image.CHANNEL_GREEN, x, y);
                    b = image.get(Image.CHANNEL_BLUE, x, y);
                } else {
                    r = image.get(0, x, y);
                    g = image.get(0, x, y);
                    b = image.get(0, x, y);
                }
                buff.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return buff;
    }

    /**
     * Updates the image
     */
    private void updateImage() {
        icon.setImage(getBuffered(value).getScaledInstance(150, 150, BufferedImage.SCALE_FAST));
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
