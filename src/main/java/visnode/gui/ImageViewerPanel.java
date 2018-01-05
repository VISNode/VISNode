package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import org.paim.commons.Image;
import org.paim.commons.ImageConverter;
import org.paim.commons.RenderingOptions;
import visnode.application.ActionExportImage;
import visnode.application.VISNode;
import visnode.commons.swing.WindowFactory;

/**
 * Image viewer dialog
 */
public class ImageViewerPanel extends JPanel {

    /** Image */
    private final Image image;
    /** Image scroll pane */
    private JScrollPane scrollPane;

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
        add(buildToolbar(), BorderLayout.NORTH);
        add(buildInfo(), BorderLayout.SOUTH);
        add(buildImage());
    }

    /**
     * Builds the tool bar
     *
     * @return
     */
    private JComponent buildToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.add(new ActionExportImage(image));
        return toolbar;
    }

    /**
     * Builds the info panel
     *
     * @return JComponent
     */
    private JComponent buildInfo() {
        JLabel info = new JLabel();
        info.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 0));
        info.setText(String.format("%sx%s pixels     range(%s/%s)",
                image.getWidth(),
                image.getHeight(),
                image.getPixelValueRange().getLower(),
                image.getPixelValueRange().getHigher()
        ));
        return info;
    }

    /**
     * Builds the image component
     *
     * @return JComponent
     */
    private JComponent buildImage() {
        scrollPane = new JScrollPane(new ImageContainer());
        scrollPane.setMaximumSize(VISNode.get().getMainPanel().getSize());
        return scrollPane;
    }

    /**
     * Image container
     */
    private class ImageContainer extends JComponent {

        /** Maximum zoom */
        private static final float MAX_ZOOM = 20;
        /** Minimum zoom */
        private static final float MIN_ZOOM = 0.2f;
        /** Original image */
        private final BufferedImage buff;
        /** Container width */
        private int width;
        /** Container height */
        private int height;
        /** Container zoom */
        private float zoom;

        public ImageContainer() {
            RenderingOptions options = VISNode.get().getModel().getUserPreferences().getRenderingOptions();
            buff = ImageConverter.toBufferedImage(image, options);
            width = image.getWidth();
            height = image.getHeight();
            zoom = 1;
            initGui();
            initEvents();
        }

        /**
         * Initializes the interface
         */
        private void initGui() {
            setPreferredSize(new Dimension(width, height));
        }

        /**
         * Initializes the events
         */
        private void initEvents() {
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Point point = e.getPoint();
                    if (e.isControlDown()) {
                        removeZoom(point);
                    } else {
                        addZoom(point);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            AffineTransform previousTransform = g2D.getTransform();
            g2D.scale(zoom, zoom);
            g2D.drawImage(buff, 0, 0, null);
            g2D.setTransform(previousTransform);
        }

        /**
         * Update de image zoom
         *
         * @param point
         */
        private void updateZoom(Point point) {
            int oldWidth = width;
            int oldHeight = height;
            width = (int) (buff.getWidth() * zoom);
            height = (int) (buff.getHeight() * zoom);
            setPreferredSize(new Dimension(width, height));
            // Calc the new x position
            int imagePosX = point.x;
            int newImagePosX = imagePosX + (int) (imagePosX * (((double) (width - oldWidth)) / oldWidth));
            int difX = newImagePosX - imagePosX;
            // Calc the new y position
            int imagePosY = point.y;
            int newImagePosY = imagePosY + (int) (imagePosY * (((double) (height - oldHeight)) / oldHeight));
            int difY = newImagePosY - imagePosY;
            // Calc the scrool point
            Point scrollPoint = scrollPane.getViewport().getViewPosition();
            scrollPoint.x += difX;
            scrollPoint.y += difY;
            scrollPane.getViewport().setViewPosition(scrollPoint);
            revalidate();
            repaint();
        }

        /**
         * Adds zoom
         *
         * @param point
         */
        public void addZoom(Point point) {
            zoom += Math.pow(1, zoom);
            if (zoom > MAX_ZOOM) {
                zoom = MAX_ZOOM;
            }
            updateZoom(point);
        }

        /**
         * Removes zoom
         *
         * @param point
         */
        public void removeZoom(Point point) {
            zoom -= Math.pow(1, zoom);
            if (zoom < MIN_ZOOM) {
                zoom = MIN_ZOOM;
            }
            updateZoom(point);
        }

    }

}
