/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.application.WebCamCapture;
import visnode.pdi.process.ImageInput;

/**
 * Web cam input
 */
public class WebcamInput implements ImageInput {

    @Override
    public Observable<Image> getImageObservable() {
        Subject<Image> subject = BehaviorSubject.create();
        WebCamCapture.get().capture((image) -> {
            subject.onNext(ImageFactory.buildRGBImage(image));
        });
        return subject;
    }
    
}
