package visnode.pdi.process;

import java.util.List;
import java.util.stream.Collectors;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.paim.commons.BinaryImage;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.pdi.BinaryLabelingProcess.ExtractedObject;
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
    public ObjectExtractionProcess(@Input("objectList") ObjectList objectList, @Input("script") ScriptValue script) {
        this.objectList = objectList;
        this.scriptRunner = new ScriptRunner(script);
        this.resultImage = ImageFactory.buildEmptyImage();
    }

    @Override
    public void process() {
        if (objectList == null) {
            return;
        }
        Object obj = scriptRunner.invokeFunction("process", objectList);
        if (obj != null) {
            if (obj instanceof ExtractedObject) {
                resultImage = ((ExtractedObject) obj).getMatrix();
            }
            if (obj instanceof ScriptObjectMirror) {
                ScriptObjectMirror res = (ScriptObjectMirror) obj;
                List<ExtractedObject> list = res.values().stream().map((o) -> (ExtractedObject) o).collect(Collectors.toList());
                resultImage = resultFromObjects(list);
            }
            if (obj instanceof List) {
                resultImage = resultFromObjects((List<ExtractedObject>) obj);
            }
        }
    }
    
    /**
     * Creates the result from a list of objects
     * 
     * @param list
     * @return resultFromObjects
     */
    private Image resultFromObjects(List<ExtractedObject> list) {
        BinaryImage result = null;
        for (ExtractedObject extractedObject : list) {
            if (result == null) {
                result = extractedObject.getMatrix();
            } else {
                result = extractedObject.getMatrix().union(result);
            }
        }
        return result;
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
