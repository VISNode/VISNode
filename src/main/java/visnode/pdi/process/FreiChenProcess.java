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

/**
 * Edge detection using the Frei-chen process
 */
public class FreiChenProcess implements visnode.pdi.Process {

    /** Frei-chen process */
    private final org.paim.pdi.FreiChenProcess process;

    /**
     * Creates a new Frei-chen process
     *
     * @param image
     */
    public FreiChenProcess(@Input("image") Image image) {
        Image resultImage = image;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        }
        this.process = new org.paim.pdi.FreiChenProcess(new Image(resultImage));

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
