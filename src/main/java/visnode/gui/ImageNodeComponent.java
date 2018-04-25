package visnode.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.paim.commons.Image;
import org.paim.commons.ImageConverter;
import org.paim.commons.ImageFactory;
import org.paim.commons.RenderingOptions;
import visnode.application.VISNode;

/**
 * Image component
 */
public class ImageNodeComponent extends JComponent implements ParameterComponent<Image> {

    /** Thumbnail size */
    private static final int THUMBNAIL_SIZE = 150;
    /** Rendering options listener */
    private final Runnable renderingOptionsListener;
    /** Image */
    private Image value;
    /** Image */
    private BufferedImage image;

    /**
     * Creates a new image component
     */
    public ImageNodeComponent() {
        this.value = ImageFactory.buildEmptyImage();
        initGui();
        renderingOptionsListener = this::updateImage;
        VISNode.get().getController().addRenderingOptionsListener(renderingOptionsListener);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        VISNode.get().getController().removeRenderingOptionsListener(renderingOptionsListener);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        updateImage();
        setPreferredSize(new Dimension(THUMBNAIL_SIZE, THUMBNAIL_SIZE));
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
     * Updates the image
     */
    private void updateImage() {
        updateImage(value);
    }

    /**
     * Updates the image
     */
    private void updateImage(Image value) {
        RenderingOptions options = VISNode.get().getModel().getUserPreferences().getRenderingOptions();
        BufferedImage original = ImageConverter.toBufferedImage(value, options);
        int size = Math.max(original.getWidth(), original.getHeight());
        int newWidth = THUMBNAIL_SIZE * original.getWidth() / size;
        int newHeight = THUMBNAIL_SIZE * original.getHeight() / size;
        int x = (THUMBNAIL_SIZE - newWidth) / 2;
        int y = (THUMBNAIL_SIZE - newHeight) / 2;
        BufferedImage newImage = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        java.awt.Image scaledImage = original.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        newImage.getGraphics().drawImage(scaledImage, x, y, null);
        image = newImage;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setValue(Image value) {
        this.value = value;
        SwingUtilities.invokeLater(() -> {
            updateImage(value);
        });
    }

    @Override
    public void addValueListener(ValueListener valueListener) {
        // This component is read only
    }

}
