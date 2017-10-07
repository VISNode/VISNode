/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.pdi.process;

import java.io.File;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.application.InputReader;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 *
 * @author jouwee
 */
public class ExtraInputProcess implements visnode.pdi.Process {
    
    private File file;
    private Image image;

    public ExtraInputProcess(@Input("file") File file) {
        if (file == null) {
            this.image = ImageFactory.buildEmptyImage();
        } else {
            try {
                this.image = new InputReader().read(file);
            } catch (Exception e) {
                e.printStackTrace();
            };

        }
        this.file = file;
    }    
    
    @Override
    public void process() {
        
    }
    
    /**
     * Returns the image
     *
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return image;
    }
    
}
