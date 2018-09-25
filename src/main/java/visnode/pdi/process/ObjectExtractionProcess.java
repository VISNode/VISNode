package visnode.pdi.process;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.paim.commons.BinaryImage;
import org.paim.commons.ImageFactory;
import org.paim.pdi.ExtractedObject;
import org.paim.pdi.ObjectList;
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
    /** Object List output */
    private List<ExtractedObject> objectListOut;
    /** Script runner */
    private final ScriptRunner scriptRunner;
    /** The result image */
    private BinaryImage resultImage;

    /**
     * Creates a new invert colors process
     *
     * @param objectList
     * @param script
     */
    public ObjectExtractionProcess(@Input("objectList") ObjectList objectList, @Input("script") ScriptValue script) {
        this.objectList = objectList;
        this.objectListOut = objectList != null ? new ObjectList(objectList) : objectList;
        this.scriptRunner = new ScriptRunner(script);
        this.resultImage = ImageFactory.buildBinaryImage(1, 1);
    }

    @Override
    public void process() {
        if (objectList == null) {
            return;
        }
        Object obj = scriptRunner.invokeFunction("process", objectList);
        if (obj != null) {
            if (obj instanceof ExtractedObject) {
                objectListOut.clear();
                objectListOut.add(((ExtractedObject) obj));
                resultImage = ((ExtractedObject) obj).getMatrix();
            }
            if (obj instanceof ScriptObjectMirror) {
                ScriptObjectMirror res = (ScriptObjectMirror) obj;
                List<ExtractedObject> list = res.values().stream().map((o) -> (ExtractedObject) o).collect(Collectors.toList());
                objectListOut = list;
                resultImage = resultFromObjects(list);
            }
            if (obj instanceof List) {
                objectListOut = (List<ExtractedObject>) obj;
                resultImage = resultFromObjects((List<ExtractedObject>) obj);
            }
        } else {
            objectListOut.clear();
        }
    }
    
    /**
     * Creates the result from a list of objects
     * 
     * @param list
     * @return resultFromObjects
     */
    private BinaryImage resultFromObjects(List<ExtractedObject> list) {
        BinaryImage result = null;
        for (ExtractedObject extractedObject : list) {
            if (result == null) {
                result = extractedObject.getMatrix();
            } else {
                result = extractedObject.getMatrix().union(result);
            }
        }
        if (list.size() == 1) {
            BinaryImage result1 = ImageFactory.buildBinaryImage(result.getWidth(), result.getHeight());
            for (int x = 0; x < result1.getWidth(); x++) {
                for (int y = 0; y < result1.getHeight(); y++) {
                    result1.set(x, y, result.get(x, y));
                }
            }
            return result1;
        }
        return result;
    }

    /**
     * Returns the output image
     *
     * @return BinaryImage
     */
    @Output("image")
    public BinaryImage getImage() {
        return resultImage;
    }
    
    /**
     * Returns the output image
     *
     * @return BinaryImage
     */
    @Output("objectList")
    public List<ExtractedObject> getObjectList() {
        return objectListOut;
    }

}
