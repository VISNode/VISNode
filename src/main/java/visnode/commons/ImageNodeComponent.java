package visnode.commons;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import visnode.commons.Image;
import visnode.commons.ImageFactory;
import visnode.gui.ParameterComponent;
import visnode.gui.ValueListener;

/**
 * Image component
 */
public class ImageNodeComponent extends JComponent implements ParameterComponent<Image> {

    /** Image */
    private Image value;
    /** Image */
    private ImageIcon ico;

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
    }

    /**
     * Builds the image
     *
     * @return JComponent
     */
    private JComponent buildImage() {
        this.ico = new ImageIcon();
        return new JLabel(ico);
    }

    private BufferedImage getBuffered(Image image) {
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
        ico.setImage(getBuffered(value).getScaledInstance(150, 150, BufferedImage.SCALE_FAST));
        repaint();
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
