package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
    /** Pixel hover */
    private JLabel pixelSelected;

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
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel info = new JLabel();
        info.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 0));
        info.setText(String.format("%sx%s pixels     range(%s/%s)",
                image.getWidth(),
                image.getHeight(),
                image.getPixelValueRange().getLower(),
                image.getPixelValueRange().getHigher()
        ));
        pixelSelected = new JLabel();
        panel.add(info, BorderLayout.CENTER);
        panel.add(pixelSelected, BorderLayout.EAST);
        return panel;
    }
    
    /**
     * Returns the pixel selected RGB
     * 
     * @param color
     * @return String
     */
    private String getPixelSelectedRGB(Color color) {
        return String.format("rgb(%s, %s, %s)", 
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );
    }
    
    /**
     * Builds the image component
     *
     * @return JComponent
     */
    private JComponent buildImage() {
        scrollPane = ScrollFactory.pane(new ImageContainer()).create();
        scrollPane.setMaximumSize(VISNode.get().getMainPanel().getSize());
        return scrollPane;
    }

    /**
     * Image container
     */
    private class ImageContainer extends JComponent {

        /** Zoom lookup table */
        private final float[] ZOOM_TABLE = {
            0.1f, 0.125f, 0.25f, 0.5f, 0.71f, 1, 1.5f, 2, 2.5f, 3.5f, 5, 7, 10,
            13, 20, 25, 35, 50, 70};
        /** Original image */
        private final BufferedImage buff;
        /** Container width */
        private int width;
        /** Container height */
        private int height;
        /** Image x */
        private int x;
        /** Image y */
        private int y;
        /** Container zoom */
        private int zoom;
        /** Zoom multiplication factor */
        private double zoomFactor;

        /**
         * Creates a new image container
         */
        public ImageContainer() {
            RenderingOptions options = VISNode.get().getModel().getUserPreferences().getRenderingOptions();
            buff = ImageConverter.toBufferedImage(image, options);
            width = image.getWidth();
            height = image.getHeight();
            zoom = 5;
            zoomFactor = factorFromValue(zoom);
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
            addMouseWheelListener((MouseWheelEvent e) -> {
                Point point = e.getPoint();
                if (e.isControlDown()) {
                    addZoom(point, -e.getWheelRotation());
                }
            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = (Graphics2D) buffer.createGraphics();
                    g2d.scale(zoomFactor, zoomFactor);
                    g2d.translate(x, y);
                    g2d.drawImage(buff, 0, 0, null);
                    g2d.dispose();
                    pixelSelected.setText(getPixelSelectedRGB(new Color(buffer.getRGB(e.getX(), e.getY()))));
                }

            });
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(zoomFactor, zoomFactor);
            x = y = 0; 
            if (g2d.getClip() != null) {
                x = Math.max(0, (g2d.getClipBounds().width - buff.getWidth()) / 2);
                y = Math.max(0, (g2d.getClipBounds().height - buff.getHeight()) / 2);
                g2d.translate(x, y);
            }
            g2d.drawImage(buff, 0, 0, null);
            g2d.dispose();
        }

        /**
         * Update de image zoom
         */
        private void updateZoom() {
            zoomFactor = factorFromValue(zoom);
            int oldWidth = width;
            int oldHeight = height;
            Rectangle oldScroll = scrollPane.getViewport().getViewRect();
            width = (int) (buff.getWidth() * zoomFactor);
            height = (int) (buff.getHeight() * zoomFactor);
            setPreferredSize(new Dimension(width, height));
            // Calc the scrool point
            Point scrollPoint = scrollPane.getViewport().getViewPosition();
            scrollPoint.x = (int) (((float) oldScroll.x / oldWidth) * width);
            scrollPoint.y = (int) (((float) oldScroll.y / oldHeight) * height);
            scrollPane.getViewport().setViewPosition(scrollPoint);
            revalidate();
            repaint();
        }

        /**
         * Adds zoom
         *
         * @param point
         */
        public void addZoom(Point point, int ammount) {
            zoom += ammount;
            if (zoom >= ZOOM_TABLE.length) {
                zoom = ZOOM_TABLE.length - 1;
            }
            if (zoom < 0) {
                zoom = 0;
            }
            updateZoom();
        }

        /**
         * Converts a zoom do the factor
         *
         * @param value
         * @return double
         */
        private double factorFromValue(int value) {
            return ZOOM_TABLE[value];
        }

    }

}
