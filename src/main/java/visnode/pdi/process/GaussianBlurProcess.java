/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.pdi.process;

import org.paim.commons.Image;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.ConvolutionProcess;

/**
 * Gaussian Blur process
 */
public class GaussianBlurProcess extends ConvolutionProcess {

    /** Sigma */
    private final double sigma;
    
    /**
     * Creates a new Gaussian blur process
     * 
     * @param image 
     * @param sigma 
     * @param maskSize 
     */
    public GaussianBlurProcess(@Input("image") Image image, @Input("sigma") Double sigma, @Input("maskSize") Integer maskSize) {
        super(image, maskSize);
        this.sigma = sigma;
    }

    @Override
    protected double[] getKernel() {
        return GaussKernelGenerator.buildKernel(sigma, maskSize);
    }

    /**
     * Returns the output image
     * 
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return super.getOutput();
    }

}