package visnode.pdi.process;

import org.torax.commons.Image;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Process for getting image informations
 */
public class InformationProcess implements Process {

    /** Image */
    private final Image image;

    /** Average */
    private int average;

    /**
     * Creates a new information process
     *
     * @param image
     */
    public InformationProcess(@Input("image") Image image) {
        this.image = image;
    }

    /**
     * Process the average value
     */
    private void processAverage() {
        int total = 0;
        for (int channel = 0; channel < image.getChannelCount(); channel++) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    total += image.get(channel, x, y);
                }
            }
        }
        int size = image.getChannelCount() * 
                image.getHeight() * 
                image.getWidth();
        average = total / size;
    }

    @Override
    public void process() {
        processAverage();
    }
    
    /**
     * Returns the average
     * 
     * @return int
     */
    @Output("average")
    public int getAverage()  {
        return average;
    }

}
