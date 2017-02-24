package visnode.application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import visnode.commons.Image;
import visnode.executor.Node;

/**
 * Image component
 */
public class ImageNodeComponent extends JComponent {

    /** Model */
    private final Node node;
    /** Image */
    private ImageIcon ico;

    /**
     * Creates a new image component
     *
     * @param node
     */
    public ImageNodeComponent(Node node) {
        this.node = node;
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
        ico.setImage(getBuffered((Image) node.getAttribute("image")).getScaledInstance(150, 150, BufferedImage.SCALE_FAST));
    }

}
