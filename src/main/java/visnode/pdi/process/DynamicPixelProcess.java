package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.pdi.PixelProcess;
import visnode.application.ScriptRunner;
import visnode.commons.ScriptValue;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Dynamic pixel process
 */
public class DynamicPixelProcess extends PixelProcess<Image> implements Process {

    /** The image */
    private final Image dynamicImage;
    /** Script runner */
    private final ScriptRunner scriptRunner;

    public DynamicPixelProcess(@Input("image") Image image, @Input("script") ScriptValue script) {
        super(image);
        Image resultImage;
        if (image == null) {
            resultImage = ImageFactory.buildEmptyImage();
        } else {
            resultImage = ImageFactory.
                    buildEmptyImage(Image.CHANNELS_GRAYSCALE,
                            image.getWidth(),
                            image.getHeight(),
                            image.getPixelValueRange());
        }
        this.dynamicImage = resultImage;
        this.scriptRunner = new ScriptRunner(script);
        setFinalizer(() -> {
            setOutput(dynamicImage);
        });
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        Object ret = scriptRunner.invokeFunction("process", channel, x, y, value);
        if (ret != null) {
            int pixel = Integer.valueOf(ret.toString());
            dynamicImage.set(channel, x, y, pixel);
        }
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
