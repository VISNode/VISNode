package visnode.commons;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import java.io.File;
import java.util.Arrays;
import org.paim.commons.Image;
import visnode.application.ExceptionHandler;
import visnode.application.InputReader;
import visnode.pdi.process.ImageInput;

/**
 * Multi files
 */
public class MultiFileInput implements ImageInput {

    /** Files */
    private final File[] file;
    /** File selected */
    private final int index;

    public MultiFileInput() {
        this.file = new File[0];
        this.index = 0;
    }

    public MultiFileInput(File file) {
        this(new File[] {file}, 0);
    }

    public MultiFileInput(File[] file, int index) {
        this.file = file;
        this.index = index;
    }
    
    @Override
    public Observable<Image> getImageObservable() {
        try {
            return BehaviorSubject.createDefault(new InputReader().read(getFile()));
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
            return null;
        }
    }

    public int getIndex() {
        return index;
    }

    public File getFile() {
        if (file.length == 0) {
            return null;
        }
        return file[index];
    }

    public File[] getFiles() {
        return file;
    }

    public int getSize() {
        return file.length;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Arrays.deepHashCode(this.file);
        hash = 89 * hash + this.index;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MultiFileInput other = (MultiFileInput) obj;
        if (this.index != other.index) {
            return false;
        }
        if (!Arrays.deepEquals(this.file, other.file)) {
            return false;
        }
        return true;
    }

}
