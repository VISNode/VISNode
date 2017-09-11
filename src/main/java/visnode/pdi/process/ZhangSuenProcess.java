package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Zhang Suen process for edge detection
 */
public class ZhangSuenProcess implements Process {

    /** Zhang Suen process */
    private final org.paim.pdi.ZhangSuenProcess process;
    
    /**
     * Creates a new Zhang Suen process
     * 
     * @param image 
     */
    public ZhangSuenProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.ZhangSuenProcess(new Image(resultImage));
        
    }

    @Override
    public void process() {
        process.process();
    }
    
    /**
     * Returns the output image
     *
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return process.getOutput();
    }

}