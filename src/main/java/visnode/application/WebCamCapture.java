package visnode.application;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Class responsible for capture images for the web cam
 */
public class WebCamCapture {

    /** Instance */
    private static WebCamCapture instance;
    /** WebCam */
    private Webcam webCam;
    /** WebCam state */
    private boolean stopCamera;

    private WebCamCapture() {
        this.stopCamera = true;
    }

    /**
     * Executes the capture
     *
     * @param captureable
     */
    public void capture(Captureable captureable) {
        stopCamera = false;
        if (Webcam.getWebcams().isEmpty()) {
            return;
        }
        webCam = Webcam.getWebcams().get(0);
        webCam.getDevice().setResolution(new Dimension(500, 485));
        webCam.open();
        startWebCamStream(captureable);
    }

    /**
     * Returns true if the capture is running
     * 
     * @return boolean
     */
    public boolean isRunning() {
        return !stopCamera;
    }

    /**
     * Stop the capture
     */
    public void stop() {
        if (webCam != null) {
            webCam.close();
        }
        stopCamera = true;
    }

    /**
     * Starts the capture
     *
     * @param captureable
     */
    private void startWebCamStream(Captureable captureable) {
        Thread th = new Thread(() -> {
            while (!stopCamera) {
                try {
                    BufferedImage tmp = webCam.getImage();
                    if (tmp != null) {
                        captureable.captured(tmp);
                    }
                } catch (Exception e) {
                    ExceptionHandler.get().handle(e);
                }
            }
        });
        th.setDaemon(true);
        th.start();
    }

    /**
     * Returns the singleton instance
     *
     * @return WebCamCapture
     */
    public static WebCamCapture get() {
        if (instance == null) {
            instance = new WebCamCapture();
        }
        return instance;
    }

    /**
     * Capture event
     */
    public static interface Captureable {

        public void captured(BufferedImage image);
    }
}
