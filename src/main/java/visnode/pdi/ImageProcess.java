package visnode.pdi;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;

/**
 * Process that works on top of a image
 * 
 * @param <O> output type
 */
public abstract class ImageProcess<O> implements Process {

    /** Image */
    protected final Image image;
    /** Output of the process */
    private O output;
    /** Process initializer */
    private Runnable initializer;
    /** Process finisher */
    private Runnable finisher;

    /**
     * Creates a new image process
     * 
     * @param image 
     */
    public ImageProcess(Image image) {
        if (image == null) {
            this.image = ImageFactory.buildEmptyImage();
        } else {
            this.image = image;
        }
    }

    @Override
    public final void process() {
        if (initializer != null) {
            initializer.run();
        }
        processImage();
        if (finisher != null) {
            finisher.run();
        }
    }

    /**
     * Process the image
     */
    protected abstract void processImage();
    
    /**
     * Returns the output
     * 
     * @return O
     */
    protected O getOutput() {
        return output;
    }

    /**
     * Sets the output
     * 
     * @param output 
     */
    protected void setOutput(O output) {
        this.output = output;
    }

    /**
     * Sets the initializer
     * 
     * @param initializer 
     */
    protected void setInitializer(Runnable initializer) {
        this.initializer = initializer;
    }

    /**
     * Sets the finisher
     * 
     * @param finisher 
     */
    protected void setFinisher(Runnable finisher) {
        this.finisher = finisher;
    }
    
}
