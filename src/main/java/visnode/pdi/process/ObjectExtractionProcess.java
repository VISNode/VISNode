package visnode.pdi.process;

import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.pdi.BinaryLabelingProcess.ObjectList;
import visnode.application.ScriptRunner;
import visnode.commons.Input;
import visnode.commons.Output;
import visnode.commons.ScriptValue;
import visnode.pdi.Process;

/**
 * Process responsible for object extractions 
 */
public class ObjectExtractionProcess implements Process {
    
    /** List of objects */
    private final ObjectList objectList;
    /** Script runner */
    private final ScriptRunner scriptRunner;    
    /** The result image */
    private Image resultImage;

    
    /**
     * Creates a new invert colors process
     *
     * @param objectList
     * @param script
     */
    public ObjectExtractionProcess(@Input("image") ObjectList objectList, @Input("script") ScriptValue script) {
        this.objectList = objectList;
        this.scriptRunner = new ScriptRunner(script);
        this.resultImage = ImageFactory.buildEmptyImage();
    }

    @Override
    public void process() {
        Object obj = scriptRunner.invokeFunction("process", objectList);
        if (obj != null) {
            resultImage = ((org.paim.pdi.BinaryLabelingProcess.ExtractedObject) obj).getMatrix();
        }
    }
    
    /**
     * Returns the output image
     * 
     * @return Image
     */
    @Output("image")
    public Image getImage() {
        return resultImage;
    }

}
