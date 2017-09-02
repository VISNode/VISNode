package visnode.pdi.process;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.pdi.PixelProcess;
import visnode.application.ExceptionHandler;
import visnode.commons.DynamicNodeValue;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.pdi.Process;

/**
 * Dynamic pixel process
 */
public class DynamicPixelProcess extends PixelProcess<Image> implements Process {

    /** The image */
    private final Image dynamicImage;
    /** Script */
    private final DynamicNodeValue script;
    /** Script invocable */
    private Invocable inv;

    public DynamicPixelProcess(@Input("image") Image image, @Input("script") DynamicNodeValue script) {
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
        this.script = script;
        buildInvocable();
        setFinalizer(() -> {
            setOutput(dynamicImage);
        });
    }

    /**
     * Builds the script invocable
     */
    private void buildInvocable() {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        try {
            if (hasScript()) {
                engine.eval(script.getValue());
                inv = (Invocable) engine;
            }
        } catch (ScriptException e) {
            ExceptionHandler.get().handle(e);
        }
    }

    @Override
    protected void process(int channel, int x, int y, int value) {
        if (hasScript() && inv != null) {
            try {
                Object ret = inv.invokeFunction("process", channel, x, y, value);
                int pixel = Integer.valueOf(ret.toString());
                dynamicImage.set(Image.CHANNEL_GRAY, x, y, pixel);
            } catch (NoSuchMethodException | ScriptException e) {
                ExceptionHandler.get().handle(e);
            }
        }
    }

    /**
     * Returns true if there is a script
     *
     * @return boolean
     */
    private boolean hasScript() {
        return script != null && script.hasValue();
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
