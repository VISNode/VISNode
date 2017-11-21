package visnode.commons;

import java.io.File;
import java.util.Arrays;

/**
 * Multi files
 */
public class MultiFile {

    /** Files */
    private final File[] file;
    /** File selected */
    private final int index;

    public MultiFile(File[] file, int index) {
        this.file = file;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public File getFile() {
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
        final MultiFile other = (MultiFile) obj;
        if (this.index != other.index) {
            return false;
        }
        if (!Arrays.deepEquals(this.file, other.file)) {
            return false;
        }
        return true;
    }

}
