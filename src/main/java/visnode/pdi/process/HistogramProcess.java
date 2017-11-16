package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Histogram process 
 */
public class HistogramProcess implements visnode.pdi.Process {

    /** Histogram process */
    private final org.paim.pdi.HistogramProcess process;
    
    /**
     * Creates a new Histogram process
     * 
     * @param image 
     */
    public HistogramProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.HistogramProcess(new Image(resultImage));
        
    }

    @Override
    public void process() {
        process.process();
    }
    
    /**
     * Returns the value that has the least occurrences. If multiple values have the same number of occurrences,
     * returns the lowest
     * 
     * @return int
     */
    @Output("valueWithLeastOccurences")
    public int getValueWithLeastOccurences() {
        if (process.getOutput() == null) {
            return 0;
        }
        return process.getOutput().getValueWithLeastOccurences();
    }

    /**
     * Returns the value that has the max occurrences inside of range. If multiple values have the same number of occurrences,
     * returns the highest
     * 
     * @return int
     */
    @Output("valueWithMaxOccurences")
    public int getValueWithMaxOccurences() {
        if (process.getOutput() == null) {
            return 0;
        }
        return process.getOutput().getValueWithMaxOccurences();
    }
    
}