/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.commons.Percentage;

/**
 * Process for blending two images
 */
public class BlendProcess implements visnode.pdi.Process {
    
    /** Brightness process*/
    private final org.paim.pdi.BlendProcess process;

    /**
     * Creates a new brightness process
     *
     * @param image
     * @param image2
     * @param weight
     */
    public BlendProcess(@Input("image") Image image, @Input("image2") Image image2, @Input("weight")@Percentage Double weight) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        weight = weight == null ? 0.5 : weight;
        this.process = new org.paim.pdi.BlendProcess(new Image(resultImage), image2, weight.floatValue());
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