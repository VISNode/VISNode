package visnode.pdi.process;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.commons.Input;
import visnode.commons.Output;

/**
 * Input process
 */
public class InputProcess implements visnode.pdi.Process {
    
    /** Extra image */
    private Observable<Image> image;

    public InputProcess(@Input("file") ImageInput input) {
        if (input == null) {
            this.image = BehaviorSubject.createDefault(ImageFactory.buildEmptyImage());
            return;
        } 
        try {
            this.image = input.getImageObservable();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }    
    
    @Override
    public void process() {
    }
    
    /**
     * Returns the image
     *
     * @return Image
     */
    @Output(value = "image", observableOf = Image.class)
    public Observable<Image> getImage() {
        return image;
    }
    
}
