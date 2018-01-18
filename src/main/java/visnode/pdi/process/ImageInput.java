package visnode.pdi.process;

import io.reactivex.Observable;
import org.paim.commons.Image;

/**
 * Generic input for the InputProcess
 */
public interface ImageInput {
    
    /**
     * Returns an observable of images
     * 
     * @return Image
     */
    public Observable<Image> getImageObservable();
    
}
